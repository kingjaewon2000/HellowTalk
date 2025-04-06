package com.example.hellowtalk.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 유저 관련 예외 코드
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, "U-000", "유저 아이디가 중복되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String description;

    ErrorCode(HttpStatus httpStatus, String code, String description) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.description = description;
    }
}
