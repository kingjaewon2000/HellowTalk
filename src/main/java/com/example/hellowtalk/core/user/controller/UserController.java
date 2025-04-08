package com.example.hellowtalk.core.user.controller;

import com.example.hellowtalk.core.user.dto.request.UserCreateRequest;
import com.example.hellowtalk.core.user.dto.response.UserCreateResponse;
import com.example.hellowtalk.core.user.service.UserService;
import com.example.hellowtalk.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserCreateResponse>> createUser(@RequestBody UserCreateRequest request) {
        UserCreateResponse response = userService.createUser(request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.success(response));
    }

}
