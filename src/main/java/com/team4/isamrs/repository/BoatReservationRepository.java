package com.team4.isamrs.repository;

import com.team4.isamrs.model.boat.BoatReservation;
import com.team4.isamrs.model.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Set;

public interface BoatReservationRepository extends JpaRepository<BoatReservation, Long> {
    Set<BoatReservation> findReservationsByStartDateBeforeOrEndDateAfter(LocalDate from, LocalDate to);
    Set<BoatReservation> findReservationsByStartDateBeforeAndEndDateAfter(LocalDate to, LocalDate from);
}
