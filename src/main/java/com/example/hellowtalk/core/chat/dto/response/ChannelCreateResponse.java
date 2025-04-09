package com.example.hellowtalk.core.chat.dto.response;

import com.example.hellowtalk.core.chat.entity.Channel;

import java.util.List;

public record ChannelCreateResponse(
        Long channelId,
        String channelType,
        String channelName,
        List<Long> userIds
) {

    public static ChannelCreateResponse toResponse(Channel channel, List<Long> userIds) {
        return new ChannelCreateResponse(
                channel.getChannelId(),
                channel.getType().name(),
                channel.getName(),
                userIds
        );
    }
}

