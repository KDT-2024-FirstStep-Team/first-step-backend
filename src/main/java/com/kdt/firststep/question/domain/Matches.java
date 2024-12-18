package com.kdt.firststep.question.domain;


import com.kdt.firststep.user.domain.Users;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "matches")
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id2", nullable = false)
    private Users user2;

    private LocalDate marriageDate;

    @Column(nullable = false, updatable = false)
    private Timestamp matchDate;

    @Enumerated(EnumType.STRING)
    private MatchStatus status; // 연인/결혼 상태

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoupleDailyQuestion> coupleDailyQuestions;
    // status 는 dating: 연인 , married: 결혼
    // 으로 하고 연동 코드는둘 중 한명만 입력하면 되도록 설정하려고 하늗데(이건 일단 보류)
}
