package com.example.hellowtalk.core.friend.controller;

import com.example.hellowtalk.core.friend.dto.request.FriendCreateRequest;
import com.example.hellowtalk.core.friend.dto.response.FriendInfoResponse;
import com.example.hellowtalk.core.friend.service.FriendService;
import com.example.hellowtalk.global.annotation.LoginUser;
import com.example.hellowtalk.global.auth.AuthUser;
import com.example.hellowtalk.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FriendInfoResponse>>> getFriends(@LoginUser AuthUser authUser) {
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(friendService.getFriends(authUser.userId())));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createFriend(
            @LoginUser AuthUser authUser,
            @RequestBody FriendCreateRequest request
    ) {
        friendService.createFriend(request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.success());
    }
}
