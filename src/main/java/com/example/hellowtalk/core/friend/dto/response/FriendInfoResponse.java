package com.example.hellowtalk.core.friend.dto.response;

import com.example.hellowtalk.core.friend.entity.Friend;
import com.example.hellowtalk.core.user.entity.User;

public record FriendInfoResponse(
        Long userId,
        String name,
        String status) {

    public static FriendInfoResponse toResponse(Friend friend) {
        User user = friend.getRequestedUser();

        return new FriendInfoResponse(
                user.getUserId(),
                user.getName(),
                user.getStatus().toString()
        );
    }
}
