package com.kdt.firststep.community.domain;

import com.kdt.firststep.community.domain.embeddable.UserPostId;
import com.kdt.firststep.user.domain.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostLikes {

    @EmbeddedId
    private UserPostId userPostId;

    @CreationTimestamp
    private LocalDateTime likeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")  // EmbeddedId의 userId를 참조
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")  // EmbeddedId의 postId를 참조
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Posts post;
}
