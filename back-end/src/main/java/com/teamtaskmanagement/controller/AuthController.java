package com.teamtaskmanagement.controller;

import com.teamtaskmanagement.dto.request.LoginRequest;
import com.teamtaskmanagement.dto.request.LogoutRequest;
import com.teamtaskmanagement.dto.response.ApiResponse;
import com.teamtaskmanagement.dto.response.LoginResponse;
import com.teamtaskmanagement.dto.response.UserProfileResponse;
import com.teamtaskmanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok("Login successful", authService.login(request));
    }

    @GetMapping("/me")
    public ApiResponse<UserProfileResponse> me() {
        return ApiResponse.ok("Success", authService.me());
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@Valid @RequestBody LogoutRequest request) {
        authService.logout(request);
        return ApiResponse.ok("Logout successful", null);
    }
}
