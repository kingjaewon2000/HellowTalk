package com.example.hellowtalk.core.user.dto.response;

import com.example.hellowtalk.core.user.entity.User;

public record LoginResponse(
        Long userId,
        String username,
        String accessToken) {

    public static LoginResponse toResponse(User user, String accessToken) {
        return new LoginResponse(
                user.getUserId(),
                user.getUsername(),
                accessToken
        );
    }
}
