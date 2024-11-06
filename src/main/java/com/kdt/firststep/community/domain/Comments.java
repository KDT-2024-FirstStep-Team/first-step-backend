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
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false, updatable = false)
    private Posts post;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime registerDate;

    @LastModifiedDate // <- 요건 JPA전용 어노테이션, @UpdateTimestamp는 hibernate
    private LocalDateTime modifyDate;

    public Comments(Users user, Posts post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }
}
