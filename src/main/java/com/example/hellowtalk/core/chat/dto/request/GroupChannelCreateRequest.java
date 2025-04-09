package com.example.hellowtalk.core.chat.dto.request;

import java.util.List;

public record GroupChannelCreateRequest(
        String channelName,
        List<Long> userIds) {
}
