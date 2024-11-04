package com.kdt.firststep.community.domain;

import com.kdt.firststep.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class) // 추가 필요
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    // 관계 설정
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private boolean category;
    private String title;
    private String content;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime registerDate;

    @LastModifiedDate // 엔티티가 수정될 때 자동으로 현재 시간이 입력됨
    private LocalDateTime modifyDate;

    private int likes;
    private int comments;

    // mappedBy = 연결, cascade = 데이터 변경시 자식 엔티티에게 변경사항 전파,orphanRemoval = post에서 comment가 제거되면 DB에서도 자동제거
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;
}
