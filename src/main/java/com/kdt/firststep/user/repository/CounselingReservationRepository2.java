package com.kdt.firststep.user.repository;

import com.kdt.firststep.counselor.domain.CounselingReservation;
import com.kdt.firststep.counselor.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounselingReservationRepository2 extends JpaRepository<CounselingReservation, Integer> {
    List<CounselingReservation> findByUser_UserIdAndStatusIn(Integer userId, List<ReservationStatus> statuses);
}
