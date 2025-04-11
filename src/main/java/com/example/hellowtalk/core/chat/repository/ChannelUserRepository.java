package com.example.hellowtalk.core.chat.repository;

import com.example.hellowtalk.core.chat.entity.ChannelUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelUserRepository extends JpaRepository<ChannelUser, Long> {

    List<ChannelUser> findAllByUser_UserId(Long userUserId);
}
