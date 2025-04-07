package com.example.hellowtalk.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 인증 관련 예외 코드
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "A-000", "인증에 실패했습니다."),

    // 토큰 관련 예외 코드
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "T-000", "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "T-001", "토큰이 유효하지 않습니다."),

    // 유저 관련 예외 코드
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, "U-000", "유저 아이디가 중복되었습니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "U-001", "존재하지 않는 회원입니다.");



    private final HttpStatus httpStatus;
    private final String code;
    private final String description;

    ErrorCode(HttpStatus httpStatus, String code, String description) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.description = description;
    }
}
