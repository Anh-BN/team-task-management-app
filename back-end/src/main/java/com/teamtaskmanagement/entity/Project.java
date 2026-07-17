package com.teamtaskmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "[Project]")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "project_name")
    private String projectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public Integer getProjectId() { return projectId; }
    public String getProjectName() { return projectName; }
    public User getOwner() { return owner; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public void setOwner(User owner) { this.owner = owner; }
}
