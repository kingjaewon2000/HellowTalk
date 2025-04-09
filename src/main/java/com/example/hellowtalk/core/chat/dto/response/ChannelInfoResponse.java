package com.example.hellowtalk.core.chat.dto.response;

import com.example.hellowtalk.core.chat.entity.Channel;
import com.example.hellowtalk.core.chat.entity.ChannelUser;

import java.time.LocalDateTime;

public record ChannelInfoResponse(
        Long channelId,
        String channelType,
        String channelName,
        String lastMessage,
        LocalDateTime lastMessageAt) {

    public static ChannelInfoResponse toResponse(ChannelUser channelUser) {
        Channel channel = channelUser.getChannel();

        return new ChannelInfoResponse(
                channel.getChannelId(),
                channel.getType().name(),
                channel.getName(),
                "",
                LocalDateTime.now()
        );
    }

}
