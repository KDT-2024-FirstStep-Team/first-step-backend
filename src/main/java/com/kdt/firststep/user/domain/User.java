package com.kdt.firststep.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

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
    private boolean personalityCheck = false;

    @Column(name = "couple_check", nullable = false)
    private boolean coupleCheck = false;

    @Column(name = "counselor_check", nullable = false)
    private boolean counselorCheck = false;

    @Lob
    @Column(name = "family_url")
    private String familyUrl;

    private int coin;

    @Column(name = "marital_status", nullable = false)
    private boolean maritalStatus = false;

}
