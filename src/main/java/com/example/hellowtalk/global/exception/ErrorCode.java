package com.example.hellowtalk.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 인증 관련 예외 코드
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "인증이 필요한 작업입니다."),

    // 토큰 관련 예외 코드
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),

    // 유저 관련 예외 코드
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, "유저 아이디가 중복되었습니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),

    // 채팅방 관련 예외 코드
    SELF_INVITATION_NOT_ALLOW(HttpStatus.BAD_REQUEST, "자기 자신을 채팅방에 초대하는 것은 허용되지 않습니다."),
    INVALID_PARTICIPANT_IDS(HttpStatus.BAD_REQUEST, "초대받은 유저 목록 ID가 유효하지 않습니다."),
    CHANNEL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 동일한 사용자와의 채팅방이 존재합니다.");


    private final HttpStatus status;
    private final String description;

    ErrorCode(HttpStatus status, String description) {
        this.status = status;
        this.description = description;
    }
}
