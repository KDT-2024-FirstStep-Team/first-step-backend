package com.kdt.firststep.community.domain;

import com.kdt.firststep.community.domain.embeddable.UserPostId;
import com.kdt.firststep.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class PostSaves {
    @EmbeddedId
    private UserPostId userPostId;

    @CreationTimestamp
    private LocalDateTime saveDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")  // EmbeddedId의 userId를 참조
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")  // EmbeddedId의 postId를 참조
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Posts post;
}
