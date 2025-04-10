package com.example.hellowtalk.core.user.service;

import com.example.hellowtalk.core.user.dto.request.LoginRequest;
import com.example.hellowtalk.core.user.dto.response.LoginResponse;
import com.example.hellowtalk.core.user.entity.User;
import com.example.hellowtalk.core.user.repository.UserRepository;
import com.example.hellowtalk.global.auth.JwtProvider;
import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hellowtalk.core.user.dto.response.LoginResponse.toResponse;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username());

        if (user == null) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        if (!verifyPassword(request.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String accessToken = jwtProvider.createAccessToken(user.getUserId(), user.getUsername());

        return toResponse(user, accessToken);
    }

    private boolean verifyPassword(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }

}
