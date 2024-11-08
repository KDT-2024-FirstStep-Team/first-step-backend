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
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    // 관계 설정
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private Users user;

    private boolean category;
    private String title;
    private String content;

    @CreationTimestamp
    private LocalDateTime registerDate;

    @LastModifiedDate // 엔티티가 수정될 때 자동으로 현재 시간이 입력됨
    private LocalDateTime modifyDate;

    private Integer likes=0;

    @Transient
    private Integer comments=0;

    // mappedBy = 연결, cascade = 데이터 변경시 자식 엔티티에게 변경사항 전파,orphanRemoval = post에서 comment가 제거되면 DB에서도 자동제거
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> commentsList;

    @PostLoad
    private void calculateComments() {
        this.comments = (commentsList != null) ? commentsList.size() : 0;
    }

    // 게시글 등록 생성자
    public Posts(Users user, boolean category, String title, String content) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
    }

//    // 게시글 수정 생성자
//    public Posts(int postId, Users user, boolean category, String title, String content) {
//        this.user = user;
//        this.category = category;
//        this.title = title;
//        this.content = content;
//        this.modifyDate = LocalDateTime.now();
//    }
}
