package com.example.hellowtalk.core.service;

import com.example.hellowtalk.core.dto.request.UserCreateRequest;
import com.example.hellowtalk.core.entity.User;
import com.example.hellowtalk.core.repository.UserRepository;
import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.hellowtalk.core.entity.LoginStatus.OFFLINE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new CustomException(ErrorCode.DUPLICATED_USERNAME);
        }

        User user = User.builder()
                .username(request.username())
                .password(request.password())
                .name(request.name())
                .status(OFFLINE)
                .lastLoginAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}
