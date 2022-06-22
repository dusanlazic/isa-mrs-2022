package com.team4.isamrs.repository;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.reservation.ReservationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.Optional;

public interface ReservationReportRepository extends JpaRepository<ReservationReport, Long> {
    Boolean existsReservationReportByReservation(Reservation reservation);

    Optional<ReservationReport> findByReservation(Reservation reservation);

    Collection<ReservationReport> findByApprovalStatusEquals(ApprovalStatus approvalStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM ReservationReport r WHERE r.id = ?1")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Optional<ReservationReport> lockGetById(Long id);
}
