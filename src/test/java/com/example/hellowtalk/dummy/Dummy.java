package com.example.hellowtalk.dummy;

import com.example.hellowtalk.core.user.entity.LoginStatus;
import com.example.hellowtalk.core.user.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

public class Dummy {

    public static User mockUser() {
        return User.builder()
                .userId(1L)
                .username("test")
                .password(BCrypt.hashpw("test1234", BCrypt.gensalt()))
                .name("테스트 계정")
                .status(LoginStatus.OFFLINE)
                .lastLoginAt(LocalDateTime.now())
                .build();
    }

}
