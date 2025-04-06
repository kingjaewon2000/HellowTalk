package com.example.hellowtalk.global.auth;

public interface JwtProvider {

    String createAccessToken(String subject);

    String getSubjectFromToken(String token);

}
