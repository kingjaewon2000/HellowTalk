package com.example.hellowtalk.core.user.service;

import com.example.hellowtalk.core.user.dto.request.UserCreateRequest;
import com.example.hellowtalk.core.user.entity.User;
import com.example.hellowtalk.core.user.repository.UserRepository;
import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.hellowtalk.core.user.entity.LoginStatus.OFFLINE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new CustomException(ErrorCode.DUPLICATED_USERNAME);
        }

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .status(OFFLINE)
                .lastLoginAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}
