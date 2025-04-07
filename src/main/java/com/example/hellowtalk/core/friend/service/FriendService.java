package com.example.hellowtalk.core.friend.service;

import com.example.hellowtalk.core.friend.dto.request.FriendCreateRequest;
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
                .map(f -> new FriendInfoResponse(
                        f.getRequestedUser().getUserId(),
                        f.getRequestedUser().getName(),
                        f.getRequestedUser().getStatus().toString()
                ))
                .toList();
    }

    @Transactional
    public void createFriend(FriendCreateRequest request) {
        User user = userRepository.findByUserId(request.userId()).orElseThrow();
        User friendUser = userRepository.findByUserId(request.friendId()).orElseThrow();

        Friend friend = Friend.builder()
                .requesterUser(user)
                .requestedUser(friendUser)
                .status(ACCEPTED)
                .build();

        friendRepository.save(friend);
    }
}
