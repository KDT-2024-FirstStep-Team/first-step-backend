package com.kdt.firststep.counselor.service;

import com.kdt.firststep.counselor.domain.CounselorProfile;
import com.kdt.firststep.counselor.domain.UserPersonalityType;
import com.kdt.firststep.counselor.dto.response.RecommendedCounselorResponseDto;
import com.kdt.firststep.counselor.exception.UserPersonalityNotFoundException;
import com.kdt.firststep.counselor.repository.CounselorProfileRepository;
import com.kdt.firststep.counselor.repository.UserPersonalityTypeRepository;
import com.kdt.firststep.user.domain.Users;
import com.kdt.firststep.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CounselorMatchingServiceImpl implements CounselorMatchingService {
    private final UserRepository userRepository;
    private final CounselorProfileRepository counselorProfileRepository;
    private final UserPersonalityTypeRepository userPersonalityTypeRepository;

    /**
     * 맞춤 상담사 TOP5 추천
     */
    @Override
    public List<RecommendedCounselorResponseDto> getRecommendedCounselors(Integer userId) {
        // 1. 사용자 조회
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        // 2. 사용자의 성향분석 결과 조회
        UserPersonalityType userType = userPersonalityTypeRepository.findByUser(user)
                .orElseThrow(() -> new UserPersonalityNotFoundException("사용자의 성향 분석 결과가 없습니다. 성향 분석을 먼저 진행하세요."));

        // 3. 기본 조건을 만족하는 상담사 목록 조회 (평점 3.0 이상)
        List<CounselorProfile> counselors = counselorProfileRepository.findAllByAverageRatingGreaterThanEqual(3.0);

        // 성향 분석 결과가 없는 상담사 제외
        List<CounselorProfile> filteredCounselors = counselors.stream()
                .filter(counselor -> userPersonalityTypeRepository.findByUser(counselor.getUser()).isPresent()) // 성향 분석 결과가 있는 상담사만 필터링
                .collect(Collectors.toList());

        // 4. 각 상담사별 매칭 점수 계산
        List<CounselorMatchingScore> matchingScores = counselors.stream()
                .map(counselor -> calculateMatchingScore(userType, user, counselor))
                .filter(score -> score.getTotalScore() > 0)  // 매칭 점수가 0인 상담사 제외
                .sorted(Comparator.comparing(CounselorMatchingScore::getTotalScore).reversed())
                .limit(5)  // TOP 5
                .collect(Collectors.toList());

        // 5. 상위 5명 선정 및 응답 DTO 변환
        return matchingScores.stream()
                .map(score -> createResponseDto(score.getCounselor(), score.getTotalScore()))
                .collect(Collectors.toList());
    }

    /**
     * 매칭 점수 계산 (총점 100점)
     */
    private CounselorMatchingScore calculateMatchingScore(
            UserPersonalityType userType,
            Users user,
            CounselorProfile counselor) {

        Integer totalScore = 0;

        // 1. 성향 매칭 점수 계산 (60점)
        Integer personalityScore = calculatePersonalityScore(userType, counselor);

        // 2. 연령대 매칭 점수 계산 (20점)
        Integer ageScore = calculateAgeMatchingScore(user, counselor.getUser());

        // 3. 자녀 유무 매칭 점수 계산 (10점)
        Integer childScore = calculateChildStatusScore(user, counselor.getUser());

        // 4. 평점 점수 계산 (10점)
        Integer ratingScore = calculateRatingScore(counselor);

        totalScore = personalityScore + ageScore + childScore + ratingScore;

        return new CounselorMatchingScore(counselor, totalScore);
    }

    private Integer calculatePersonalityScore(UserPersonalityType userType, CounselorProfile counselor) {
        // 유형별 점수 차이 계산 및 가중치 적용
        Integer matchCount = 0;
        Integer score = 0;

        // 각 유형별 점수 차이 계산
        Map<String, Integer> userScores = new HashMap<>();
        userScores.put("communication", userType.getCommunicationSkill()); // 의사소통
        userScores.put("conflict", userType.getConflictManagement()); // 갈등관리
        userScores.put("financial", userType.getFinancialManagement()); // 재정관리
        userScores.put("stress", userType.getStressManagement()); // 스트레스관리
        userScores.put("values", userType.getPersonalValues()); // 가치관

        // 사용자의 각 성향 점수를 높은 순으로 정렬
        List<Map.Entry<String, Integer>> sortedScores = userScores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        // 각 유형별로 매칭 여부 확인 및 점수 부여
        for (int i = 0; i < sortedScores.size(); i++) {
            String type = sortedScores.get(i).getKey();
            Integer userScore = sortedScores.get(i).getValue();
            Integer counselorScore = getCounselorScore(counselor, type);

            // 매칭 점수 계산
            if (Math.abs(userScore - counselorScore) <= 1) {
                matchCount++;
                switch (i) {
                    case 0 -> score += 25;  // 1순위 유형
                    case 1 -> score += 20;  // 2순위 유형
                    case 2 -> score += 10;  // 3순위 유형
                    case 3 -> score += 3;   // 4순위 유형
                    case 4 -> score += 2;   // 5순위 유형
                }
            }
        }

        // 3가지 미만 매칭 시 기본 점수 10점
        return matchCount < 3 ? 10 : score;
    }

    private Integer getCounselorScore(CounselorProfile counselor, String type) {
        // Optional 로 감싸고, 결과가 없으면 기본 점수(0) 반환
        return userPersonalityTypeRepository.findByUser(counselor.getUser())
                .map(counselorType -> switch (type) {
                    case "communication" -> counselorType.getCommunicationSkill();
                    case "conflict" -> counselorType.getConflictManagement();
                    case "financial" -> counselorType.getFinancialManagement();
                    case "stress" -> counselorType.getStressManagement();
                    case "values" -> counselorType.getPersonalValues();
                    default -> throw new IllegalArgumentException("잘못된 유형입니다.");
                })
                .orElse(0); // 성향 분석 결과가 없는 경우 기본 점수 반환
    }

    /**
     * 연령대 매칭 점수 (20점)
     */
    private Integer calculateAgeMatchingScore(Users user, Users counselor) {
        Integer userAge = calculateAge(user.getBirth());
        Integer counselorAge = calculateAge(counselor.getBirth());

        Integer userAgeGroup = userAge / 10;
        Integer counselorAgeGroup = counselorAge / 10;

        if (userAgeGroup.equals(counselorAgeGroup)) {
            return 20;  // 같은 연령대
        } else if (Math.abs(userAgeGroup - counselorAgeGroup) == 1) {
            return 10;  // 인접 연령대
        }
        return 0;
    }

    /**
     * 자녀 유무 (10점)
     */
    private Integer calculateChildStatusScore(Users user, Users counselor) {
        return user.getChildStatus().equals(counselor.getChildStatus()) ? 10 : 0;
    }

    /**
     * 평점 점수 (10점)
     */
    private Integer calculateRatingScore(CounselorProfile counselor) {
        Double avgRating = counselorProfileRepository.findAverageRatingByCounselorId(counselor.getCounselorId())
                .orElse(0.0);

        if (avgRating >= 4.5) return 10;
        if (avgRating >= 4.0) return 7;
        if (avgRating >= 3.0) return 5;
        return 0;
    }

    private Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    private RecommendedCounselorResponseDto createResponseDto(
            CounselorProfile counselor,
            Integer matchingScore) {
        Double avgRating = counselorProfileRepository
                .findAverageRatingByCounselorId(counselor.getCounselorId())
                .orElse(0.0);
        List<String> badges = counselorProfileRepository
                .findBadgesByCounselorId(counselor.getCounselorId());

        return RecommendedCounselorResponseDto.from(counselor, matchingScore, avgRating, badges);
    }

    @Getter
    @AllArgsConstructor
    private static class CounselorMatchingScore {
        private CounselorProfile counselor;
        private Integer totalScore;
    }
}
