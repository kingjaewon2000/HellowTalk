package com.example.hellowtalk.core.chat.controller;

import com.example.hellowtalk.core.chat.dto.request.DirectChannelCreateRequest;
import com.example.hellowtalk.core.chat.dto.request.GroupChannelCreateRequest;
import com.example.hellowtalk.core.chat.dto.response.ChannelCreateResponse;
import com.example.hellowtalk.core.chat.dto.response.ChannelInfoResponse;
import com.example.hellowtalk.core.chat.service.ChannelService;
import com.example.hellowtalk.global.annotation.LoginUser;
import com.example.hellowtalk.global.auth.AuthUser;
import com.example.hellowtalk.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChannelInfoResponse>>> getChannels(@LoginUser AuthUser authUser) {
        List<ChannelInfoResponse> responses = channelService.getChannels(authUser.userId());

        return ResponseEntity.ok()
                .body(ApiResponse.success(responses));
    }

    @PostMapping("/direct")
    public ResponseEntity<ApiResponse<ChannelCreateResponse>> createDirectChannel(
            @LoginUser AuthUser authUser,
            @RequestBody DirectChannelCreateRequest request) {
        ChannelCreateResponse response = channelService.createDirectChannel(authUser.userId(), request);

        return ResponseEntity.ok()
                .body(ApiResponse.success(response));
    }

    @PostMapping("/group")
    public ResponseEntity<ApiResponse<ChannelCreateResponse>> createGroupChannel(
            @LoginUser AuthUser authUser,
            @RequestBody GroupChannelCreateRequest request) {
        ChannelCreateResponse response = channelService.createGroupChannel(authUser.userId(), request);

        return ResponseEntity.ok()
                .body(ApiResponse.success(response));
    }

}
