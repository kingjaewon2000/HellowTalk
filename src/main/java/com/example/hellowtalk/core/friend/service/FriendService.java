package com.example.hellowtalk.core.friend.service;

import com.example.hellowtalk.core.friend.dto.request.FriendAddRequest;
import com.example.hellowtalk.core.friend.dto.response.FriendAddResponse;
import com.example.hellowtalk.core.friend.dto.response.FriendInfoResponse;
import com.example.hellowtalk.core.friend.entity.FriendShipLinkId;
import com.example.hellowtalk.core.friend.entity.Friendship;
import com.example.hellowtalk.core.friend.entity.FriendshipLink;
import com.example.hellowtalk.core.friend.entity.RelationStatus;
import com.example.hellowtalk.core.friend.repository.FriendshipLinkCustomRepository;
import com.example.hellowtalk.core.friend.repository.FriendshipLinkRepository;
import com.example.hellowtalk.core.friend.repository.FriendshipRepository;
import com.example.hellowtalk.core.user.entity.User;
import com.example.hellowtalk.core.user.service.UserService;
import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.hellowtalk.core.friend.entity.RelationStatus.PENDING;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipLinkRepository friendshipLinkRepository;
    private final FriendshipLinkCustomRepository friendshipLinkCustomRepository;
    private final UserService userService;

    public List<FriendInfoResponse> getFriends(Long userId) {
        List<FriendshipLink> friends = friendshipLinkCustomRepository.findAllByUserId(userId);

        return friends.stream()
                .map(FriendInfoResponse::toResponse)
                .toList();
    }

    @Transactional
    public FriendAddResponse addFriend(Long requesterUserId, FriendAddRequest request) {
        User requester = userService.findById(requesterUserId);
        User requested = userService.findByUsername(request.username());

        validFriendshipRequest(requester, requested);

        Friendship friendship = createFriendship(PENDING);
        friendshipRepository.save(friendship);

        FriendshipLink friendshipLink1 = createFriendshipLink(friendship, requester);
        FriendshipLink friendshipLink2 = createFriendshipLink(friendship, requested);

        friendshipLinkRepository.save(friendshipLink1);
        friendshipLinkRepository.save(friendshipLink2);

        Long requesterId = friendshipLink1.getUser().getUserId();
        Long requestedId = friendshipLink2.getUser().getUserId();

        return FriendAddResponse.toResponse(friendship, requesterId, requestedId);
    }

    private Friendship createFriendship(RelationStatus status) {
        return Friendship.builder()
                .status(status)
                .build();
    }

    private FriendshipLink createFriendshipLink(Friendship friendship, User user) {
        return FriendshipLink.builder()
                .friendshipLinkId(new FriendShipLinkId(friendship.getFriendshipId(), user.getUserId()))
                .friendship(friendship)
                .user(user)
                .build();
    }

    private void validFriendshipRequest(User requester, User requested) {
        validateSelfAddFriend(requester, requested);
        validateExistsFriend(requester.getUserId(), requested.getUserId());
    }

    private void validateSelfAddFriend(User user, User friendUser) {
        if (user.equals(friendUser)) {
            throw new CustomException(ErrorCode.NOT_ALLOW_SELF_ADD_FRIEND);
        }
    }

    private void validateExistsFriend(Long requesterUserId, Long requestedUserId) {
        if (friendshipLinkCustomRepository.existsFriend(requesterUserId, requestedUserId)) {
            throw new CustomException(ErrorCode.NOT_ALLOW_ALREADY_ADDED_FRIEND);
        }
    }

}
