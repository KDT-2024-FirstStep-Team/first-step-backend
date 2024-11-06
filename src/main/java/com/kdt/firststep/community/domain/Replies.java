package com.kdt.firststep.community.domain;

import com.kdt.firststep.user.domain.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Replies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reply_Id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false, updatable = false)
    private Comments comment;

    private String content;

    @CreationTimestamp
    private LocalDateTime register_date;

    @LastModifiedDate
    private LocalDateTime modify_date;

    public Replies(Users user, Comments comment, String content) {
        this.user = user;
        this.comment = comment;
        this.content = content;
    }
}
