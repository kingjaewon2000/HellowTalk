package com.example.hellowtalk.global.interceptor;

import com.example.hellowtalk.global.auth.AuthUser;
import com.example.hellowtalk.global.auth.JwtProvider;
import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class StompInterceptor implements ChannelInterceptor {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            return message;
        }

        if (isConnect(accessor)) {
            handleConnect(message, accessor);
        } else if (isSendOrSubscribe(accessor)) {
            handleSendOrSubscribe(accessor);
        }

        return ChannelInterceptor.super.preSend(message, channel);
    }

    private void handleConnect(Message<?> message, StompHeaderAccessor accessor) {
        String token = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
        token = parseToken(token);

        AuthUser authUser = jwtProvider.getAuthUserFromToken(token);
        StompPrincipal stompPrincipal = new StompPrincipal(authUser.username());
        accessor.setUser(stompPrincipal);
    }

    private void handleSendOrSubscribe(StompHeaderAccessor accessor) {
        Principal user = accessor.getUser();

        if (user == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    private String parseToken(String token) {
        if (!(StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        return token.substring(BEARER_PREFIX.length());
    }

    private boolean isConnect(StompHeaderAccessor accessor) {
        return accessor != null && StompCommand.CONNECT.equals(accessor.getCommand());
    }

    private boolean isSendOrSubscribe(StompHeaderAccessor accessor) {
        return accessor != null && (StompCommand.SEND.equals(accessor.getCommand()) || StompCommand.SUBSCRIBE.equals(accessor.getCommand()));
    }

}
