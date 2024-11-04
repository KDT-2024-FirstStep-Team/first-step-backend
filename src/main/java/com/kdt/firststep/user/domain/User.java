package com.kdt.firststep.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "user_name")
    private String userName;

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
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

    @Column(name = "personality_check")
    private boolean personalityCheck;

    @Column(name = "couple_check")
    private boolean coupleCheck;

    @Column(name = "counselor_check")
    private boolean counselorCheck;

    @Lob
    @Column(name = "family_url")
    private String familyUrl;

    private int coin;

    @Column(name = "marital_status")
    private boolean maritalStatus;

}
