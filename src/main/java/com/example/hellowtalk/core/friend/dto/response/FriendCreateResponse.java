package com.example.hellowtalk.core.friend.dto.response;

import com.example.hellowtalk.core.friend.entity.Friend;

public record FriendCreateResponse(
        Long friendId,
        Long requesterId,
        Long requestedId,
        String relationStatus) {

    public static FriendCreateResponse toResponse(Friend friend) {
        return new FriendCreateResponse(
                friend.getFriendId(),
                friend.getRequesterUser().getUserId(),
                friend.getRequesterUser().getUserId(),
                friend.getStatus().name()
        );
    }
}
