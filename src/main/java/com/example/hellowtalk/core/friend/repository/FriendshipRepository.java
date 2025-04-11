package com.example.hellowtalk.core.friend.repository;

import com.example.hellowtalk.core.friend.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

}
