package com.teamtaskmanagement.service;

import com.teamtaskmanagement.dto.response.ActivityLogResponse;
import com.teamtaskmanagement.dto.response.PagedResponse;
import com.teamtaskmanagement.entity.Task;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface ActivityLogService {
    void createLog(Task task, Integer actorUserId, String action, String fieldName, String oldValue, String newValue);
    PagedResponse<ActivityLogResponse> getProjectLogs(Integer projectId, String action, Integer userId, Integer taskId,
                                                      LocalDateTime fromDate, LocalDateTime toDate, String search, Pageable pageable);
    PagedResponse<ActivityLogResponse> getTaskLogs(Integer taskId, Pageable pageable);
}
