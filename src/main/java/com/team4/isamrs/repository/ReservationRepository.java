package com.team4.isamrs.repository;

import com.team4.isamrs.model.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Set<Reservation> findReservationsByStartDateTimeBeforeOrEndDateTimeAfter(LocalDateTime from, LocalDateTime to);
    Set<Reservation> findReservationsByStartDateTimeBeforeAndEndDateTimeAfter(LocalDateTime to, LocalDateTime from);
    Set<Reservation> findReservationsByEndDateTimeAfter(LocalDateTime to);
    Set<Reservation> findReservationsByStartDateTimeBefore(LocalDateTime from);
}
