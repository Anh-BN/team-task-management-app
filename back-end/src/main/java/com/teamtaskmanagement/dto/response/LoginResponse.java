package com.teamtaskmanagement.dto.response;

public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserProfileResponse user;

    public LoginResponse(String accessToken, String refreshToken, UserProfileResponse user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public UserProfileResponse getUser() { return user; }
}
