package com.kdt.firststep.community.domain;

import com.kdt.firststep.community.domain.embeddable.UserPostId;
import com.kdt.firststep.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Entity
@Getter
@EnableJpaAuditing
public class PostLike {

    @EmbeddedId
    private UserPostId userPostId;

    @CreationTimestamp
    private LocalDateTime likeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")  // EmbeddedId의 userId를 참조
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")  // EmbeddedId의 postId를 참조
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;
}
