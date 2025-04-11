package com.example.hellowtalk.core.chat.repository;

import org.springframework.data.repository.query.Param;

public interface ChannelUserCustomRepository {

    boolean existsOneToOneChannelByUsers(
            @Param("userId1") Long userId1,
            @Param("userId2") Long userId2
    );
}
