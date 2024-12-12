package com.kdt.firststep.counselor.domain;

import com.kdt.firststep.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_personality_types")
public class UserPersonalityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer typeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "communication_skill", nullable = false)
    private Integer communicationSkill;

    @Column(name = "conflict_management", nullable = false)
    private Integer conflictManagement;

    @Column(name = "financial_management", nullable = false)
    private Integer financialManagement;

    @Column(name = "stress_management", nullable = false)
    private Integer stressManagement;

    @Column(name = "personal_values", nullable = false)
    private Integer personalValues;

    @CreationTimestamp
    @Column(name = "analyzed_at", nullable = false)
    private LocalDateTime analyzedAt;

    @Builder
    private UserPersonalityType(Users user, Integer communicationSkill, Integer conflictManagement,
                                Integer financialManagement, Integer stressManagement,
                                Integer personalValues) {
        this.user = user;
        this.communicationSkill = communicationSkill;
        this.conflictManagement = conflictManagement;
        this.financialManagement = financialManagement;
        this.stressManagement = stressManagement;
        this.personalValues = personalValues;
    }
}
