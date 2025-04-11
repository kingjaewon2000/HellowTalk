package com.example.hellowtalk.core.friend.repository;

import com.example.hellowtalk.core.friend.entity.FriendshipLink;
import com.example.hellowtalk.core.friend.entity.QFriendship;
import com.example.hellowtalk.core.friend.entity.QFriendshipLink;
import com.example.hellowtalk.core.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendshipLinkCustomRepositoryImpl implements FriendshipLinkCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FriendshipLink> findAllByUserId(Long userId) {
        QFriendshipLink userLink = new QFriendshipLink("userLink");
        QFriendshipLink friendLink = new QFriendshipLink("friendLink");

        QUser friendUser = QUser.user;
        QFriendship sharedFriendship = QFriendship.friendship;

        return queryFactory
                .select(friendLink)
                .from(userLink)
                .innerJoin(friendLink)
                .on(userLink.friendshipLinkId.friendshipId.eq(friendLink.friendshipLinkId.friendshipId))
                .innerJoin(friendLink.user, friendUser).fetchJoin()
                .innerJoin(friendLink.friendship, sharedFriendship).fetchJoin()
                .where(
                        userLink.friendshipLinkId.userId.eq(userId),
                        friendLink.friendshipLinkId.userId.ne(userId)
                )
                .fetch();
    }

    @Override
    public boolean existsFriend(Long requesterUserId, Long requestedUserId) {
        QFriendshipLink fl1 = new QFriendshipLink("fl1");
        QFriendshipLink fl2 = new QFriendshipLink("fl2");

        Integer fetchFirst = queryFactory
                .selectOne()
                .from(fl1)
                .innerJoin(fl2)
                .on(fl1.friendshipLinkId.friendshipId.eq(fl2.friendshipLinkId.friendshipId))
                .where(
                        fl1.friendshipLinkId.userId.eq(requesterUserId),
                        fl2.friendshipLinkId.userId.eq(requestedUserId)
                )
                .fetchFirst();

        return fetchFirst != null;
    }
}
