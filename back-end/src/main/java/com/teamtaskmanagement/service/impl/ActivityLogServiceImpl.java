package com.teamtaskmanagement.service.impl;

import com.teamtaskmanagement.dto.response.ActivityLogResponse;
import com.teamtaskmanagement.dto.response.PagedResponse;
import com.teamtaskmanagement.entity.ActivityLog;
import com.teamtaskmanagement.entity.Task;
import com.teamtaskmanagement.exception.ResourceNotFoundException;
import com.teamtaskmanagement.repository.ActivityLogRepository;
import com.teamtaskmanagement.repository.ActivityLogSpecification;
import com.teamtaskmanagement.repository.TaskRepository;
import com.teamtaskmanagement.repository.UserRepository;
import com.teamtaskmanagement.service.ActivityLogService;
import com.teamtaskmanagement.service.CurrentUserService;
import com.teamtaskmanagement.service.ProjectAuthorizationService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {
    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CurrentUserService currentUserService;
    private final ProjectAuthorizationService authorizationService;

    public ActivityLogServiceImpl(ActivityLogRepository activityLogRepository, UserRepository userRepository,
                                  TaskRepository taskRepository, CurrentUserService currentUserService,
                                  ProjectAuthorizationService authorizationService) {
        this.activityLogRepository = activityLogRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.currentUserService = currentUserService;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public void createLog(Task task, Integer actorUserId, String action, String fieldName, String oldValue, String newValue) {
        var actor = userRepository.findById(actorUserId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        ActivityLog log = new ActivityLog();
        log.setTask(task);
        log.setUser(actor);
        log.setAction(action);
        log.setFieldName(fieldName);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setCreatedAt(LocalDateTime.now());
        activityLogRepository.save(log);
    }

    public PagedResponse<ActivityLogResponse> getProjectLogs(Integer projectId, String action, Integer userId, Integer taskId,
                                                             LocalDateTime fromDate, LocalDateTime toDate, String search, Pageable pageable) {
        authorizationService.checkCanViewProject(projectId, currentUserService.getCurrentUserId());
        var spec = ActivityLogSpecification.filter(projectId, action, userId, taskId, fromDate, toDate, search);
        return new PagedResponse<>(activityLogRepository.findAll(spec, pageable).map(this::toResponse));
    }

    public PagedResponse<ActivityLogResponse> getTaskLogs(Integer taskId, Pageable pageable) {
        Task task = taskRepository.findWithProjectByTaskId(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        authorizationService.checkCanViewProject(task.getProject().getProjectId(), currentUserService.getCurrentUserId());
        return new PagedResponse<>(activityLogRepository.findByTaskTaskId(taskId, pageable).map(this::toResponse));
    }

    private ActivityLogResponse toResponse(ActivityLog log) {
        ActivityLogResponse r = new ActivityLogResponse();
        r.logId = log.getLogId();
        r.taskId = log.getTask() == null ? null : log.getTask().getTaskId();
        r.taskName = log.getTask() == null ? null : log.getTask().getTaskName();
        r.userId = log.getUser() == null ? null : log.getUser().getUserId();
        r.userFullName = log.getUser() == null ? null : log.getUser().getFullName();
        r.userAvatarUrl = log.getUser() == null ? null : log.getUser().getAvatarUrl();
        r.action = log.getAction();
        r.fieldName = log.getFieldName();
        r.oldValue = log.getOldValue();
        r.newValue = log.getNewValue();
        r.createdAt = log.getCreatedAt();
        r.displayMessage = buildMessage(r);
        return r;
    }

    private String buildMessage(ActivityLogResponse r) {
        if ("StatusChanged".equals(r.action)) return r.userFullName + " changed status from " + r.oldValue + " to " + r.newValue;
        if ("Assigned".equals(r.action)) return r.userFullName + " assigned task from " + r.oldValue + " to " + r.newValue;
        if ("Created".equals(r.action)) return r.userFullName + " created task " + r.newValue;
        if ("AttachmentAdded".equals(r.action)) return r.userFullName + " added attachment " + r.newValue;
        return r.userFullName + " updated " + r.fieldName + " from " + r.oldValue + " to " + r.newValue;
    }
}
