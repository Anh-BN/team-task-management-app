package com.teamtaskmanagement.service.impl;

import com.teamtaskmanagement.dto.response.MemberProgressResponse;
import com.teamtaskmanagement.dto.response.ProjectProgressResponse;
import com.teamtaskmanagement.entity.Project;
import com.teamtaskmanagement.entity.Task;
import com.teamtaskmanagement.exception.ResourceNotFoundException;
import com.teamtaskmanagement.repository.ProjectMemberRepository;
import com.teamtaskmanagement.repository.ProjectRepository;
import com.teamtaskmanagement.repository.TaskRepository;
import com.teamtaskmanagement.service.CurrentUserService;
import com.teamtaskmanagement.service.ProjectAuthorizationService;
import com.teamtaskmanagement.service.ProjectProgressService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectProgressServiceImpl implements ProjectProgressService {
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository memberRepository;
    private final TaskRepository taskRepository;
    private final CurrentUserService currentUserService;
    private final ProjectAuthorizationService authorizationService;

    public ProjectProgressServiceImpl(ProjectRepository projectRepository, ProjectMemberRepository memberRepository,
                                      TaskRepository taskRepository, CurrentUserService currentUserService,
                                      ProjectAuthorizationService authorizationService) {
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.taskRepository = taskRepository;
        this.currentUserService = currentUserService;
        this.authorizationService = authorizationService;
    }

    public ProjectProgressResponse getProgressSummary(Integer projectId) {
        authorizationService.checkCanViewProject(projectId, currentUserService.getCurrentUserId());
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        List<Task> tasks = taskRepository.findByProjectProjectId(projectId);
        ProjectProgressResponse r = new ProjectProgressResponse();
        r.projectId = projectId;
        r.projectName = project.getProjectName();
        r.totalTasks = tasks.size();
        r.pendingTasks = count(tasks, "Pending");
        r.inProgressTasks = count(tasks, "InProgress");
        r.doneTasks = count(tasks, "Done");
        r.incompleteTasks = tasks.stream().filter(t -> !"Done".equals(t.getStatus())).count();
        r.overdueTasks = tasks.stream().filter(this::isOverdue).count();
        r.completionPercentage = percent(r.doneTasks, r.totalTasks);
        r.memberProgress = memberRepository.findByProjectProjectId(projectId).stream().map(pm -> {
            var u = pm.getUser();
            var assigned = tasks.stream().filter(t -> t.getAssignee() != null && t.getAssignee().getUserId().equals(u.getUserId())).toList();
            MemberProgressResponse m = new MemberProgressResponse();
            m.userId = u.getUserId();
            m.fullName = u.getFullName();
            m.avatarUrl = u.getAvatarUrl();
            m.assignedTasks = assigned.size();
            m.pendingTasks = count(assigned, "Pending");
            m.inProgressTasks = count(assigned, "InProgress");
            m.doneTasks = count(assigned, "Done");
            m.overdueTasks = assigned.stream().filter(this::isOverdue).count();
            m.completionPercentage = percent(m.doneTasks, m.assignedTasks);
            return m;
        }).toList();
        return r;
    }

    private long count(List<Task> tasks, String status) {
        return tasks.stream().filter(t -> status.equals(t.getStatus())).count();
    }

    private boolean isOverdue(Task task) {
        return task.getPlannedEndDate() != null && task.getPlannedEndDate().isBefore(LocalDate.now()) && !"Done".equals(task.getStatus());
    }

    private double percent(long done, long total) {
        return total == 0 ? 0 : done * 100.0 / total;
    }
}
