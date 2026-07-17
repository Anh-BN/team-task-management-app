package com.teamtaskmanagement.mapper;

import com.teamtaskmanagement.dto.response.UserProfileResponse;
import com.teamtaskmanagement.entity.User;

public class UserMapper {
    private UserMapper() {}

    public static UserProfileResponse toProfile(User user) {
        String role = user.getRole() == null ? null : user.getRole().getRoleName();
        return new UserProfileResponse(user.getUserId(), user.getEmail(), user.getFullName(),
                user.getPhone(), user.getAvatarUrl(), role, user.getActive());
    }
}
