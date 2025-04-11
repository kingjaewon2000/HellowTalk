package com.example.hellowtalk.core.chat.repository;

import com.example.hellowtalk.core.chat.entity.ChannelType;
import com.example.hellowtalk.core.chat.entity.QChannel;
import com.example.hellowtalk.core.chat.entity.QChannelUser;
import com.example.hellowtalk.core.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChannelUserCustomRepositoryImpl implements ChannelUserCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QChannel channel = QChannel.channel;
    private final QUser user = QUser.user;

    @Override
    public boolean existsOneToOneChannelByUsers(Long userId1, Long userId2) {
        QChannelUser cu1 = new QChannelUser("cu1");
        QChannelUser cu2 = new QChannelUser("cu2");

        Integer result = queryFactory
                .selectOne()
                .from(cu1)
                .innerJoin(cu2)
                .on(cu1.channel.channelId.eq(cu2.channel.channelId))
                .where(
                        cu1.user.userId.eq(userId1),
                        cu2.user.userId.eq(userId2),
                        cu1.channel.type.eq(ChannelType.ONE_TO_ONE)
                )
                .fetchFirst();

        return result != null;
    }
}
