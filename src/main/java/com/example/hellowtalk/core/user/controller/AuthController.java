package com.example.hellowtalk.core.user.controller;

import com.example.hellowtalk.core.user.dto.request.LoginRequest;
import com.example.hellowtalk.core.user.dto.response.LoginResponse;
import com.example.hellowtalk.core.user.service.AuthService;
import com.example.hellowtalk.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(authService.login(request)));
    }

}
