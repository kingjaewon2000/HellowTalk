package com.example.hellowtalk.core.controller;

import com.example.hellowtalk.core.dto.request.LoginRequest;
import com.example.hellowtalk.core.dto.response.LoginResponse;
import com.example.hellowtalk.core.service.AuthService;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String accessToken = authService.login(request);

        return ResponseEntity.ok().body(new LoginResponse(accessToken));
    }

}
