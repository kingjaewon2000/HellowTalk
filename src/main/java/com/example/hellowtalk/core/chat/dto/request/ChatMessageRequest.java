package com.example.hellowtalk.core.chat.dto.request;

public record ChatMessageRequest(
        Long channelId,
        MessageType type,
        String username,
        String content
) {

    public enum MessageType {
        CHAT
    }

}
