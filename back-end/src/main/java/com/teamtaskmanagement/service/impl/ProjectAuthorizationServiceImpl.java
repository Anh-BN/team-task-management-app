package com.teamtaskmanagement.service.impl;

import com.teamtaskmanagement.exception.ForbiddenException;
import com.teamtaskmanagement.repository.ProjectMemberRepository;
import com.teamtaskmanagement.repository.ProjectRepository;
import com.teamtaskmanagement.service.CurrentUserService;
import com.teamtaskmanagement.service.ProjectAuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class ProjectAuthorizationServiceImpl implements ProjectAuthorizationService {
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final CurrentUserService currentUserService;

    public ProjectAuthorizationServiceImpl(ProjectRepository projectRepository, ProjectMemberRepository projectMemberRepository,
                                           CurrentUserService currentUserService) {
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.currentUserService = currentUserService;
    }

    public boolean isProjectMember(Integer projectId, Integer userId) {
        return projectMemberRepository.existsByProjectProjectIdAndUserUserId(projectId, userId);
    }

    public boolean isProjectOwner(Integer projectId, Integer userId) {
        return projectRepository.existsByProjectIdAndOwnerUserId(projectId, userId);
    }

    public void checkCanViewProject(Integer projectId, Integer userId) {
        String role = currentUserService.getCurrentUserRole();
        if ("Admin".equals(role) || isProjectOwner(projectId, userId) || isProjectMember(projectId, userId)) return;
        throw new ForbiddenException("You do not have permission to view this project");
    }

    public void checkCanManageProject(Integer projectId, Integer userId) {
        String role = currentUserService.getCurrentUserRole();
        if ("Admin".equals(role) || ("Manager".equals(role) && isProjectOwner(projectId, userId))) return;
        throw new ForbiddenException("You do not have permission to manage this project");
    }
}
