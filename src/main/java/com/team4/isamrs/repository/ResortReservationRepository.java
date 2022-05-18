package com.team4.isamrs.repository;

import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.resort.ResortReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Set;

public interface ResortReservationRepository extends JpaRepository<ResortReservation, Long> {
    Set<ResortReservation> findReservationsByStartDateBeforeOrEndDateAfter(LocalDate from, LocalDate to);
    Set<ResortReservation> findReservationsByStartDateBeforeAndEndDateAfter(LocalDate to, LocalDate from);
}
