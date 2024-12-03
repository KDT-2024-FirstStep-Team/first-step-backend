package com.kdt.firststep.counselor.domain;


import com.kdt.firststep.user.domain.Users;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "saved_counselors")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SavedCounselor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer savedId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "counselor_id")
    private CounselorProfile counselorProfile;

    private LocalDateTime createdAt;

}

