package com.example.hellowtalk.core.friend.service;

import com.example.hellowtalk.core.friend.dto.request.FriendCreateRequest;
import com.example.hellowtalk.core.friend.dto.response.FriendCreateResponse;
import com.example.hellowtalk.core.friend.dto.response.FriendInfoResponse;
import com.example.hellowtalk.core.friend.entity.Friend;
import com.example.hellowtalk.core.friend.repository.FriendRepository;
import com.example.hellowtalk.core.user.entity.User;
import com.example.hellowtalk.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.hellowtalk.core.friend.entity.RelationStatus.ACCEPTED;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public List<FriendInfoResponse> getFriends(Long userId) {
        List<Friend> friends = friendRepository.findAllByRequesterUser_UserId(userId);

        return friends.stream()
                .map(FriendInfoResponse::toResponse)
                .toList();
    }

    @Transactional
    public FriendCreateResponse createFriend(Long userId, FriendCreateRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow();
        User friendUser = userRepository.findByUserId(request.friendId()).orElseThrow();

        Friend buildFriend = Friend.builder()
                .requesterUser(user)
                .requestedUser(friendUser)
                .status(ACCEPTED)
                .build();

        Friend friend = friendRepository.save(buildFriend);

        return FriendCreateResponse.toResponse(friend);
    }

}
