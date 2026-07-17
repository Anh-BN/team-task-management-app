package com.teamtaskmanagement.service;

import com.teamtaskmanagement.dto.response.ProjectProgressResponse;

public interface ProjectProgressService {
    ProjectProgressResponse getProgressSummary(Integer projectId);
}
