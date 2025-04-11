package com.example.hellowtalk.core.friend.dto.response;

import com.example.hellowtalk.core.friend.entity.Friendship;

public record FriendAddResponse(
        Long friendshipId,
        Long requesterId,
        Long requestedId,
        String relationStatus) {

    public static FriendAddResponse toResponse(Friendship friendship, Long requesterId, Long requestedId) {
        return new FriendAddResponse(
                friendship.getFriendshipId(),
                requesterId,
                requestedId,
                friendship.getStatus().name()
        );
    }
}
