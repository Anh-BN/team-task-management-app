package com.teamtaskmanagement.controller;

import com.teamtaskmanagement.dto.response.ApiResponse;
import com.teamtaskmanagement.dto.response.ProjectProgressResponse;
import com.teamtaskmanagement.service.ProjectProgressService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectProgressController {
    private final ProjectProgressService projectProgressService;

    public ProjectProgressController(ProjectProgressService projectProgressService) {
        this.projectProgressService = projectProgressService;
    }

    @GetMapping("/{projectId}/progress-summary")
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    public ApiResponse<ProjectProgressResponse> progress(@PathVariable Integer projectId) {
        return ApiResponse.ok("Success", projectProgressService.getProgressSummary(projectId));
    }
}
