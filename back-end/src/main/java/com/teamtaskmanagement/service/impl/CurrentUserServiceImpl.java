package com.teamtaskmanagement.service.impl;

import com.teamtaskmanagement.exception.UnauthorizedException;
import com.teamtaskmanagement.security.service.CustomUserPrincipal;
import com.teamtaskmanagement.service.CurrentUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {
    private CustomUserPrincipal principal() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserPrincipal principal)) {
            throw new UnauthorizedException("Unauthorized");
        }
        return principal;
    }

    public Integer getCurrentUserId() { return principal().getUserId(); }
    public String getCurrentUserEmail() { return principal().getEmail(); }
    public String getCurrentUserRole() { return principal().getRole(); }
    public boolean isAuthenticated() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserPrincipal;
    }
}
