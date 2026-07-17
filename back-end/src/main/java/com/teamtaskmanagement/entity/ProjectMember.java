package com.teamtaskmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "[ProjectMember]")
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_id")
    private Integer projectMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getProjectMemberId() { return projectMemberId; }
    public Project getProject() { return project; }
    public User getUser() { return user; }
    public void setProjectMemberId(Integer projectMemberId) { this.projectMemberId = projectMemberId; }
    public void setProject(Project project) { this.project = project; }
    public void setUser(User user) { this.user = user; }
}
