package com.teamtaskmanagement.dto.response;

import java.time.LocalDateTime;

public class ActivityLogResponse {
    public Integer logId;
    public Integer taskId;
    public String taskName;
    public Integer userId;
    public String userFullName;
    public String userAvatarUrl;
    public String action;
    public String fieldName;
    public String oldValue;
    public String newValue;
    public LocalDateTime createdAt;
    public String displayMessage;
}
