package com.teamtaskmanagement.service;

import com.teamtaskmanagement.dto.request.LoginRequest;
import com.teamtaskmanagement.dto.request.LogoutRequest;
import com.teamtaskmanagement.dto.response.LoginResponse;
import com.teamtaskmanagement.dto.response.UserProfileResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    UserProfileResponse me();
    void logout(LogoutRequest request);
}
