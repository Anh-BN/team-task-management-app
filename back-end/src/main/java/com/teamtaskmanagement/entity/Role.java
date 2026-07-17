package com.teamtaskmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "[Role]")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    public Integer getRoleId() { return roleId; }
    public String getRoleName() { return roleName; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
