package com.example.hellowtalk.global.common;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {

    private static final String DEFAULT_SUCCESS_MESSAGE = "성공";

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(HttpStatus.OK.value(), DEFAULT_SUCCESS_MESSAGE, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), DEFAULT_SUCCESS_MESSAGE, data);
    }

    public static <T> ApiResponse<T> success(HttpStatus status, T data) {
        return new ApiResponse<>(status.value(), DEFAULT_SUCCESS_MESSAGE, data);
    }

    public static ApiResponse<Void> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }
}
