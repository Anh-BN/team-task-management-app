package com.teamtaskmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "[ActivityLog]")
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "action")
    private String action;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Integer getLogId() { return logId; }
    public Task getTask() { return task; }
    public User getUser() { return user; }
    public String getAction() { return action; }
    public String getOldValue() { return oldValue; }
    public String getNewValue() { return newValue; }
    public String getFieldName() { return fieldName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setLogId(Integer logId) { this.logId = logId; }
    public void setTask(Task task) { this.task = task; }
    public void setUser(User user) { this.user = user; }
    public void setAction(String action) { this.action = action; }
    public void setOldValue(String oldValue) { this.oldValue = oldValue; }
    public void setNewValue(String newValue) { this.newValue = newValue; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
