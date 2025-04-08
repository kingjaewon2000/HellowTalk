package com.example.hellowtalk.core.user.service;

import com.example.hellowtalk.core.user.dto.request.UserCreateRequest;
import com.example.hellowtalk.core.user.dto.response.UserCreateResponse;
import com.example.hellowtalk.core.user.entity.User;
import com.example.hellowtalk.core.user.repository.UserRepository;
import com.example.hellowtalk.dummy.Dummy;
import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserCreateRequest request;

    @BeforeEach
    void setUp() {
        String username = "test";
        String password = "test1234";
        String name = "테스트 계정";

        request = new UserCreateRequest(username, password, name);
    }

    @Test
    @DisplayName("회원가입 요청 시 상태 코드 200 반환")
    void createUserSuccess() {
        // given
        User mockUser = Dummy.mockUser();

        when(userRepository.existsByUsername(request.username())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // when
        UserCreateResponse response = userService.createUser(request);

        // then
        assertThat(response.userId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("중복 아이디로 회원가입 시도 시 에러 반환")
    void createUserFail() {
        // given
        when(userRepository.existsByUsername(request.username())).thenReturn(true);

        // when
        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(CustomException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.DUPLICATED_USERNAME);
    }
}