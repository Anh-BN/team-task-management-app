package com.teamtaskmanagement.controller;

import com.teamtaskmanagement.dto.response.ActivityLogResponse;
import com.teamtaskmanagement.dto.response.ApiResponse;
import com.teamtaskmanagement.dto.response.PagedResponse;
import com.teamtaskmanagement.service.ActivityLogService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class ActivityLogController {
    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @GetMapping("/projects/{projectId}/activity-logs")
    public ApiResponse<PagedResponse<ActivityLogResponse>> projectLogs(
            @PathVariable Integer projectId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer taskId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ApiResponse.ok("Success", activityLogService.getProjectLogs(projectId, action, userId, taskId, fromDate, toDate, search, pageable));
    }

    @GetMapping("/tasks/{taskId}/activity-logs")
    public ApiResponse<PagedResponse<ActivityLogResponse>> taskLogs(@PathVariable Integer taskId,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.ok("Success", activityLogService.getTaskLogs(taskId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))));
    }
}
