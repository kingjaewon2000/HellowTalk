package com.example.hellowtalk.core.chat.repository;

import com.example.hellowtalk.core.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
