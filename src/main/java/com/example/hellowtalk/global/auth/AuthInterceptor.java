package com.example.hellowtalk.global.auth;

import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTH_ATTRIBUTE = "user";

    private final JwtProvider jwtProvider;

    public AuthInterceptor(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = resolveToken(request);

        if (token == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        JwtAuthentication authentication = authenticate(token);

        request.setAttribute(AUTH_ATTRIBUTE, authentication);

        return true;
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    private JwtAuthentication authenticate(String token) {
        String username = jwtProvider.getSubjectFromToken(token);

        return new JwtAuthentication(
                username,
                token
        );
    }

}
