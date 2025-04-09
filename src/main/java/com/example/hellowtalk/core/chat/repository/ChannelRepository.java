package com.example.hellowtalk.core.chat.repository;

import com.example.hellowtalk.core.chat.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
