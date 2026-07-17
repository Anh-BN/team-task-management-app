package com.teamtaskmanagement.dto.response;

public class UserProfileResponse {
    private Integer userId;
    private String email;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private String role;
    private Boolean isActive;

    public UserProfileResponse(Integer userId, String email, String fullName, String phone, String avatarUrl, String role, Boolean active) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.isActive = active;
    }

    public Integer getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getRole() { return role; }
    public Boolean getIsActive() { return isActive; }
}
