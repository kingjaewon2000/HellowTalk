package com.example.hellowtalk.core.friend.service;

import com.example.hellowtalk.core.friend.dto.request.FriendAddRequest;
import com.example.hellowtalk.core.friend.dto.response.FriendAddResponse;
import com.example.hellowtalk.core.friend.dto.response.FriendInfoResponse;
import com.example.hellowtalk.core.friend.entity.Friendship;
import com.example.hellowtalk.core.friend.entity.FriendshipLink;
import com.example.hellowtalk.core.friend.repository.FriendshipLinkCustomRepository;
import com.example.hellowtalk.core.friend.repository.FriendshipLinkRepository;
import com.example.hellowtalk.core.friend.repository.FriendshipRepository;
import com.example.hellowtalk.core.user.entity.LoginStatus;
import com.example.hellowtalk.core.user.entity.User;
import com.example.hellowtalk.core.user.service.UserService;
import com.example.hellowtalk.dummy.Dummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.example.hellowtalk.core.friend.entity.RelationStatus.ACCEPTED;
import static com.example.hellowtalk.core.friend.entity.RelationStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private FriendshipLinkRepository friendshipLinkRepository;

    @Mock
    private FriendshipLinkCustomRepository friendshipLinkCustomRepository;

    @InjectMocks
    private FriendService friendService;

    private User user;
    private User friend1;
    private User friend2;

    @BeforeEach
    void setUp() {
        user = Dummy.mockUser();
        friend1 = User.builder()
                .userId(2L)
                .username("friend1")
                .password(BCrypt.hashpw("test1234", BCrypt.gensalt()))
                .name("친구1")
                .status(LoginStatus.ONLINE)
                .lastLoginAt(LocalDateTime.now())
                .build();
        friend2 = User.builder()
                .userId(3L)
                .username("friend2")
                .password(BCrypt.hashpw("test1234", BCrypt.gensalt()))
                .name("친구2")
                .status(LoginStatus.ONLINE)
                .lastLoginAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("친구 목록 조회 성공 시")
    void 친구_목록_조회() {
        // given
        Friendship friendship = Friendship.builder()
                .friendshipId(1L)
                .status(ACCEPTED)
                .build();

        FriendshipLink friendshipLink1 = FriendshipLink.builder()
                .friendship(friendship)
                .user(friend1)
                .build();

        FriendshipLink friendshipLink2 = FriendshipLink.builder()
                .friendship(friendship)
                .user(friend2)
                .build();

        List<FriendshipLink> mockFriends = List.of(friendshipLink1, friendshipLink2);
        when(friendshipLinkCustomRepository.findAllByUserId(user.getUserId())).thenReturn(mockFriends);

        // when
        List<FriendInfoResponse> responses = friendService.getFriends(user.getUserId());

        // then
        assertThat(responses).isNotNull();
        assertThat(responses).hasSize(2);
    }

    @Test
    @DisplayName("친구 목록 조회 시 친구가 없으면 빈 리스트를 반환한다")
    void 친구_없으면_빈_목록_반환() {
        // given
        Long userId = 1L;
        when(friendshipLinkCustomRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());

        // when
        List<FriendInfoResponse> responses = friendService.getFriends(userId);

        // then
        assertThat(responses).isNotNull();
        assertThat(responses).isEmpty();
    }

    @Test
    @DisplayName("친구 추가 요청 시")
    void 친구_추가() {
        // given
        Friendship friendship = Friendship.builder()
                .friendshipId(1L)
                .status(PENDING)
                .build();

        FriendshipLink friendshipLink1 = FriendshipLink.builder()
                .friendship(friendship)
                .user(friend1)
                .build();

        FriendshipLink friendshipLink2 = FriendshipLink.builder()
                .friendship(friendship)
                .user(friend2)
                .build();

        FriendAddRequest request = new FriendAddRequest(friend1.getUsername());
        when(userService.findById(user.getUserId())).thenReturn(user);
        when(userService.findByUsername(friend1.getUsername())).thenReturn(friend1);
        when(friendshipLinkCustomRepository.existsFriend(user.getUserId(), friend1.getUserId())).thenReturn(false);
        when(friendshipRepository.save(any(Friendship.class))).thenReturn(friendship);
        when(friendshipLinkRepository.save(any(FriendshipLink.class)))
                .thenReturn(friendshipLink1)
                .thenReturn(friendshipLink2);


        // when
        FriendAddResponse response = friendService.addFriend(user.getUserId(), request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.friendshipId()).isEqualTo(1L);
        assertThat(response.requesterId()).isEqualTo(user.getUserId());
        assertThat(response.requestedId()).isEqualTo(friend1.getUserId());
        assertThat(response.relationStatus()).isEqualTo(PENDING.toString());
    }

}