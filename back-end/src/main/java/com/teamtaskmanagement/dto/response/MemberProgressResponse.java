package com.teamtaskmanagement.dto.response;

public class MemberProgressResponse {
    public Integer userId;
    public String fullName;
    public String avatarUrl;
    public long assignedTasks;
    public long pendingTasks;
    public long inProgressTasks;
    public long doneTasks;
    public long overdueTasks;
    public double completionPercentage;
}
