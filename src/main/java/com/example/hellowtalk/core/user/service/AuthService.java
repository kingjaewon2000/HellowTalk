package com.example.hellowtalk.core.user.service;

import com.example.hellowtalk.core.user.dto.request.LoginRequest;
import com.example.hellowtalk.core.user.entity.User;
import com.example.hellowtalk.core.user.repository.UserRepository;
import com.example.hellowtalk.global.auth.JwtProvider;
import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username());

        if (user == null) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        if (!user.getPassword().equals(request.password())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        return jwtProvider.createAccessToken(request.username());
    }

}
