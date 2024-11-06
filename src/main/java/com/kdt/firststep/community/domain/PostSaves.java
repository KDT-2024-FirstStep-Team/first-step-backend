package com.kdt.firststep.community.domain;

import com.kdt.firststep.community.domain.embeddable.UserPostId;
import com.kdt.firststep.user.domain.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Embeddable
@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class PostSaves {
    @EmbeddedId
    private UserPostId userPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")  // EmbeddedId의 userId를 참조
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")  // EmbeddedId의 postId를 참조
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Posts post;

    @CreationTimestamp
    private LocalDateTime saveDate;
}
