package com.example.hellowtalk.core.friend.dto.response;

import com.example.hellowtalk.core.friend.entity.FriendshipLink;
import com.example.hellowtalk.core.user.entity.User;

public record FriendInfoResponse(
        Long userId,
        String name,
        String status) {

    public static FriendInfoResponse toResponse(FriendshipLink friendshipLink) {
        User user = friendshipLink.getUser();

        return new FriendInfoResponse(
                user.getUserId(),
                user.getName(),
                user.getStatus().toString()
        );
    }
}
