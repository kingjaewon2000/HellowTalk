package com.example.hellowtalk.core.friend.service;

import com.example.hellowtalk.core.friend.dto.request.FriendCreateRequest;
import com.example.hellowtalk.core.friend.dto.response.FriendCreateResponse;
import com.example.hellowtalk.core.friend.dto.response.FriendInfoResponse;
import com.example.hellowtalk.core.friend.entity.Friend;
import com.example.hellowtalk.core.friend.repository.FriendRepository;
import com.example.hellowtalk.core.user.entity.User;
import com.example.hellowtalk.core.user.service.UserService;
import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.hellowtalk.core.friend.entity.RelationStatus.ACCEPTED;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserService userService;

    public List<FriendInfoResponse> getFriends(Long userId) {
        List<Friend> friends = friendRepository.findAllByRequesterUser_UserId(userId);

        return friends.stream()
                .map(FriendInfoResponse::toResponse)
                .toList();
    }

    @Transactional
    public FriendCreateResponse createFriend(Long userId, FriendCreateRequest request) {
        User user = userService.findById(userId);
        User friendUser = userService.findByUsername(request.username());

        if (user.equals(friendUser)) {
            throw new CustomException(ErrorCode.NOT_ALLOW_SELF_ADD_FRIEND);
        }

        if (friendRepository.existsByRequesterUser_UserIdAndRequestedUser_UserId(user.getUserId(), friendUser.getUserId())) {
            throw new CustomException(ErrorCode.NOT_ALLOW_ALREADY_ADDED_FRIEND);
        }

        Friend buildFriend = Friend.builder()
                .requesterUser(user)
                .requestedUser(friendUser)
                .status(ACCEPTED)
                .build();

        Friend friend = friendRepository.save(buildFriend);

        return FriendCreateResponse.toResponse(friend);
    }

}
