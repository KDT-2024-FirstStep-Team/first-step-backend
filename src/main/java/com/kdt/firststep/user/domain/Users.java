package com.kdt.firststep.user.domain;

import com.kdt.firststep.community.domain.Comments;
import com.kdt.firststep.community.domain.Posts;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
//@Builder
@NoArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDate birth;

    private boolean gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Lob
    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "mode_type")
    private boolean modeType;

    @Column(name = "personality_check", nullable = false)
    private boolean personalityCheck;

    @Column(name = "couple_check", nullable = false)
    private boolean coupleCheck;

    @Column(name = "counselor_check", nullable = false)
    private boolean counselorCheck;

    @Lob
    @Column(name = "family_url")
    private String familyUrl;

    private int coin;

    @Column(name = "marital_status", nullable = false)
    private boolean maritalStatus;

    // 관계설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Posts> postList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> commentsList;

}

