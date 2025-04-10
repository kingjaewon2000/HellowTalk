package com.example.hellowtalk.core.user.dto.response;

import com.example.hellowtalk.core.user.entity.User;

public record UserCreateResponse(Long userId) {

    public static UserCreateResponse toResponse(User user) {
        return new UserCreateResponse(user.getUserId());
    }
}
