package com.example.hellowtalk.core.friend.repository;

import com.example.hellowtalk.core.friend.entity.Friend;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @EntityGraph(attributePaths = { "requesterUser", "requestedUser" } )
    List<Friend> findAllByRequesterUser_UserId(Long userId);
}
