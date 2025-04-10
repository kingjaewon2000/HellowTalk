package com.example.hellowtalk.core.chat.controller;

import com.example.hellowtalk.core.chat.dto.request.ChatMessageRequest;
import com.example.hellowtalk.core.chat.dto.request.SimpleMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

import static com.example.hellowtalk.core.chat.dto.request.ChatMessageRequest.MessageType.CHAT;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/channel/{channelId}/message")
    public void sendMessage(Principal principal,
                            @DestinationVariable Long channelId,
                            @Payload SimpleMessageRequest request) {

        String username = principal.getName();

        ChatMessageRequest message = new ChatMessageRequest(
                channelId,
                CHAT,
                username,
                request.content()
        );

        String destination = "/topic/chat/channel/" + channelId + "/message";
        messagingTemplate.convertAndSend(destination, message);
    }

}
