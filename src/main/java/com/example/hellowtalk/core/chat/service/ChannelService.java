package com.example.hellowtalk.core.chat.service;

import com.example.hellowtalk.core.chat.dto.request.DirectChannelCreateRequest;
import com.example.hellowtalk.core.chat.dto.request.GroupChannelCreateRequest;
import com.example.hellowtalk.core.chat.dto.response.ChannelCreateResponse;
import com.example.hellowtalk.core.chat.dto.response.ChannelInfoResponse;
import com.example.hellowtalk.core.chat.entity.Channel;
import com.example.hellowtalk.core.chat.entity.ChannelType;
import com.example.hellowtalk.core.chat.entity.ChannelUser;
import com.example.hellowtalk.core.chat.repository.ChannelRepository;
import com.example.hellowtalk.core.chat.repository.ChannelUserRepository;
import com.example.hellowtalk.core.user.entity.User;
import com.example.hellowtalk.core.user.service.UserService;
import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.hellowtalk.core.chat.entity.ChannelType.GROUP;
import static com.example.hellowtalk.core.chat.entity.ChannelType.ONE_TO_ONE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelUserRepository channelUserRepository;
    private final UserService userService;

    public List<ChannelInfoResponse> getChannels(Long loginUserId) {
        List<ChannelUser> channelUser = channelUserRepository.findAllByUser_UserId(loginUserId);

        return channelUser.stream()
                .map(ChannelInfoResponse::toResponse)
                .toList();
    }

    @Transactional
    public ChannelCreateResponse createDirectChannel(Long loginUserId, DirectChannelCreateRequest request) {
        if (matchUserId(loginUserId, request.userId())) {
            throw new CustomException(ErrorCode.INVALID_PARTICIPANT_IDS);
        }

        User loginUser = userService.findById(loginUserId);
        User otherUser = userService.findById(request.userId());
        List<User> users = List.of(loginUser, otherUser);

        String channelName = parseChannelName(otherUser);

        return createChannelInternal(channelName, ONE_TO_ONE, users);
    }

    @Transactional
    public ChannelCreateResponse createGroupChannel(Long loginUserId, GroupChannelCreateRequest request) {
        int size = 1 + request.userIds().size();

        Set<Long> userIds = new HashSet<>(request.userIds());
        userIds.add(loginUserId);
        if (userIds.size() != size) {
            throw new CustomException(ErrorCode.INVALID_PARTICIPANT_IDS);
        }

        List<User> users = userService.findAllById(userIds);

        return createChannelInternal(request.channelName(), GROUP, users);
    }

    private ChannelCreateResponse createChannelInternal(String channelName, ChannelType channelType, List<User> participants) {
        Channel channel = Channel.builder()
                .name(channelName)
                .type(channelType)
                .build();

        channelRepository.save(channel);

        List<ChannelUser> channelUsers = participants.stream()
                .map(user -> ChannelUser.builder()
                        .channel(channel)
                        .user(user)
                        .build())
                .toList();

        channelUserRepository.saveAll(channelUsers);

        List<Long> userIds = participants.stream()
                .map(User::getUserId)
                .toList();

        return ChannelCreateResponse.toResponse(channel, userIds);
    }

    private boolean matchUserId(Long userId, Long otherUserId) {
        return userId.equals(otherUserId);
    }

    private String parseChannelName(User user) {
        return user.getName();
    }
}
