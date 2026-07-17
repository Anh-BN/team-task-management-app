package com.teamtaskmanagement.dto.response;

import java.util.List;

public class ProjectProgressResponse {
    public Integer projectId;
    public String projectName;
    public long totalTasks;
    public long pendingTasks;
    public long inProgressTasks;
    public long doneTasks;
    public long incompleteTasks;
    public long overdueTasks;
    public double completionPercentage;
    public List<MemberProgressResponse> memberProgress;
}
