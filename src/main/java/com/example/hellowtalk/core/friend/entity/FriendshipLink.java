package com.example.hellowtalk.core.friend.entity;

import com.example.hellowtalk.core.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "friendshipp_links")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class FriendshipLink implements Persistable<FriendShipLinkId> {

    @EmbeddedId
    private FriendShipLinkId friendshipLinkId;

    @MapsId("friendshipId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friendship_id")
    private Friendship friendship;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 객체가 항상 새로운 객체임을 보장.
     */
    @Override
    public FriendShipLinkId getId() {
        return friendshipLinkId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
