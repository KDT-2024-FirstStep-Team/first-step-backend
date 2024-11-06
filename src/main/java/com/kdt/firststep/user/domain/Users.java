package com.kdt.firststep.user.domain;

import com.kdt.firststep.community.domain.Comments;
import com.kdt.firststep.community.domain.Posts;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name", nullable = false)
    @NotBlank
    private String userName;

    @NotBlank
    @Column(nullable = false)
    private String nickname;

    @NotBlank
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    private LocalDate birth;

    private Boolean gender = false;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Lob
    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "mode_type")
    private Boolean modeType = false; // false: (상담사 전환 OFF), true: (상담사 전환 ON)

    @Column(name = "personality_check")
    private Boolean personalityCheck = false;

    @Column(name = "couple_check")
    private Boolean coupleCheck = false;

    @Column(name = "counselor_check")
    private Boolean counselorCheck = false;

    @Lob
    @Column(name = "family_url")
    private String familyUrl;

    private Integer coin = 0;

    @Column(name = "marital_status")
    private Boolean maritalStatus = false;

    // 관계설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Posts> postList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> commentsList;

}
