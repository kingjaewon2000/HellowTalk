package com.example.hellowtalk.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 인증 관련 예외 코드
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),

    // 토큰 관련 예외 코드
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),

    // 유저 관련 예외 코드
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, "유저 아이디가 중복되었습니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다.");



    private final HttpStatus status;
    private final String description;

    ErrorCode(HttpStatus status, String description) {
        this.status = status;
        this.description = description;
    }
}
