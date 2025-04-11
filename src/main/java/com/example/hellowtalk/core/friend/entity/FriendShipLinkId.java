package com.example.hellowtalk.core.friend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class FriendShipLinkId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long friendshipId;
    private Long userId;

}
