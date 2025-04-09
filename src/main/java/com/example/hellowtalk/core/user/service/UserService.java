package com.example.hellowtalk.core.user.service;

import com.example.hellowtalk.core.user.dto.request.UserCreateRequest;
import com.example.hellowtalk.core.user.dto.response.UserCreateResponse;
import com.example.hellowtalk.core.user.entity.User;
import com.example.hellowtalk.core.user.repository.UserRepository;
import com.example.hellowtalk.global.exception.CustomException;
import com.example.hellowtalk.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.example.hellowtalk.core.user.entity.LoginStatus.OFFLINE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    public List<User> findAllById(Set<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    @Transactional
    public UserCreateResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new CustomException(ErrorCode.DUPLICATED_USERNAME);
        }

        User user = User.builder()
                .username(request.username())
                .password(BCrypt.hashpw(request.password(), BCrypt.gensalt()))
                .name(request.name())
                .status(OFFLINE)
                .lastLoginAt(LocalDateTime.now())
                .build();

        User createUser = userRepository.save(user);

        return new UserCreateResponse(createUser.getUserId());
    }

}
