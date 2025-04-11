package com.example.hellowtalk.core.friend.repository;

import com.example.hellowtalk.core.friend.entity.FriendshipLink;

import java.util.List;

public interface FriendshipLinkCustomRepository {

    List<FriendshipLink> findAllByUserId(Long userId);

    boolean existsFriend(Long requesterUserId, Long requestedUserId);

}
