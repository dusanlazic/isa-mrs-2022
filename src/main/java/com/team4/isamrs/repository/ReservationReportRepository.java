package com.team4.isamrs.repository;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.reservation.ReservationReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ReservationReportRepository extends JpaRepository<ReservationReport, Long> {
    Boolean existsReservationReportByReservation(Reservation reservation);

    Optional<ReservationReport> findByReservation(Reservation reservation);

    Collection<ReservationReport> findByApprovalStatusEquals(ApprovalStatus approvalStatus);
}
