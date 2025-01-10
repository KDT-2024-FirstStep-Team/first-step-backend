package com.kdt.firststep.community.domain;

import com.kdt.firststep.user.domain.Users;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false, updatable = false)
    private Posts post;

    private String imageUrl;
    private LocalDateTime createdAt;

}
