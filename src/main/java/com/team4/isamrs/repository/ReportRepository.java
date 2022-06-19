package com.team4.isamrs.repository;

import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.reservation.ReservationReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<ReservationReport, Long> {
    Optional<ReservationReport> findReservationReportByReservation(Reservation reservation);
}
