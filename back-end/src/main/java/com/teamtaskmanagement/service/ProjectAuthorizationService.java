package com.teamtaskmanagement.service;

public interface ProjectAuthorizationService {
    boolean isProjectMember(Integer projectId, Integer userId);
    boolean isProjectOwner(Integer projectId, Integer userId);
    void checkCanViewProject(Integer projectId, Integer userId);
    void checkCanManageProject(Integer projectId, Integer userId);
}
