package com.example.hellowtalk.global.auth;

public interface JwtProvider {

    String createAccessToken(Long userId, String username);

    AuthUser getAuthUserFromToken(String token);

}
