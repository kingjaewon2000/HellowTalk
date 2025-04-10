package com.example.hellowtalk.core.user.service;

import com.example.hellowtalk.core.user.dto.request.LoginRequest;
import com.example.hellowtalk.core.user.dto.response.LoginResponse;
import com.example.hellowtalk.core.user.entity.User;
import com.example.hellowtalk.global.auth.JwtProvider;
import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.hellowtalk.dummy.Dummy.mockUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    private LoginRequest request;

    @BeforeEach
    void setUp() {
        String username = "test";
        String password = "test1234";
        request = new LoginRequest(username, password);
    }

    @Test
    @DisplayName("로그인 성공 시 액세스 토큰을 반환한다")
    void login() {
        // given
        User mockUser = mockUser();

        when(userService.findByUsername(request.username())).thenReturn(mockUser);
        when(jwtProvider.createAccessToken(mockUser.getUserId(), mockUser.getUsername())).thenReturn("token");

        // when
        LoginResponse response = authService.login(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.userId()).isEqualTo(mockUser.getUserId());
        assertThat(response.username()).isEqualTo(mockUser.getUsername());
    }

    @Test
    @DisplayName("로그인 시 존재하지 않는 아이디(username)을 입력하면 예외를 던진다")
    void usernameNotFound() {
        // given
        when(userService.findByUsername(request.username())).thenThrow(new CustomException(ErrorCode.NOT_FOUND_USER));

        // when
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(CustomException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NOT_FOUND_USER);

        // then
    }

    @Test
    @DisplayName("로그인 시 비밀번호 틀리면 예외를 던진다")
    void passwordIsNotMatch() {
        // given
        LoginRequest failRequest = new LoginRequest(request.username(), "fail");

        when(userService.findByUsername(request.username())).thenReturn(mockUser());

        // when
        assertThatThrownBy(() -> authService.login(failRequest))
                .isInstanceOf(CustomException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INVALID_CREDENTIALS);

        // then

    }

}