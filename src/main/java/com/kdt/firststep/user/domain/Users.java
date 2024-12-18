package com.kdt.firststep.user.domain;

import com.kdt.firststep.community.domain.Comments;
import com.kdt.firststep.community.domain.Posts;
import com.kdt.firststep.counselor.domain.CounselingReservation;
import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.community.domain.Replies;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name", nullable = false)
    @NotBlank
    private String username;

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
    private Boolean modeType;

    @Column(name = "personality_check", nullable = false)
    private Boolean personalityCheck;

    @Column(name = "couple_check", nullable = false)
    private Boolean coupleCheck;

    @Column(name = "counselor_check", nullable = false)
    private Boolean counselorCheck;

    @Lob
    @Column(name = "family_url")
    private String familyUrl;

    private Integer coin =0;

    @Column(name = "marital_status", nullable = false)
    private boolean maritalStatus;

    @Column(name = "child_status", nullable = false)
    private Boolean childStatus;

    // 관계설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Posts> postList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> commentsList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Replies> repliesList;

    // 상담관련 관계설정
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CounselorProfile counselorProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CounselingReservation> reservations;

    @OneToMany(mappedBy = "user")
    private List<SavedCounselor> savedCounselors;
}
