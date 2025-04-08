package com.example.hellowtalk.core.friend.dto.response;

public record FriendCreateResponse(
        Long friendId,
        Long requesterId,
        Long requestedId,
        String relationStatus) {
}
