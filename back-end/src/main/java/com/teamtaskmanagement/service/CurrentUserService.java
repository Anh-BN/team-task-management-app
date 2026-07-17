package com.teamtaskmanagement.service;

public interface CurrentUserService {
    Integer getCurrentUserId();
    String getCurrentUserEmail();
    String getCurrentUserRole();
    boolean isAuthenticated();
}
