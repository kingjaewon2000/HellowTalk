package com.example.hellowtalk.core.chat.repository;

import com.example.hellowtalk.core.chat.entity.Channel;
import com.example.hellowtalk.core.chat.entity.ChannelType;
import com.example.hellowtalk.core.chat.entity.ChannelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChannelUserRepository extends JpaRepository<ChannelUser, Long> {

    List<ChannelUser> findAllByUser_UserId(Long userUserId);

    @Query("SELECT c FROM ChannelUser as cu1 " +
            "INNER JOIN Channel as c on cu1.channel.channelId = c.channelId AND c.type = :channelType " +
            "INNER JOIN ChannelUser as cu2 on cu1.channel.channelId = cu2.channel.channelId " +
            "AND cu1.user.userId = :userId1 " +
            "AND cu2.user.userId = :userId2")
    Optional<Channel> findOneToOneChannelByUsers(
            @Param("userId1") Long userId1,
            @Param("userId2") Long userId2,
            @Param("channelType") ChannelType channelType
    );
}
