package com.example.hellowtalk.core.chat.entity;

import com.example.hellowtalk.core.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "channelUsers")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ChannelUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long channelUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channelId")
    private Channel channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private Long lastReadMessageId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime joinAt;
}
