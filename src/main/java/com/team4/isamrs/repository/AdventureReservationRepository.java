package com.team4.isamrs.repository;

import com.team4.isamrs.model.adventure.AdventureReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public interface AdventureReservationRepository extends JpaRepository<AdventureReservation, Long> {
    Set<AdventureReservation> findReservationsByStartDateTimeBeforeOrStartDateTimeAfter(LocalDateTime from, LocalDateTime to);
    Set<AdventureReservation> findReservationsByStartDateTimeBeforeAndEndDateTimeAfter(LocalDateTime to, LocalDateTime from);
    Set<AdventureReservation> findReservationsByEndDateTimeAfter(LocalDateTime to);
    Set<AdventureReservation> findReservationsByStartDateTimeBefore(LocalDateTime from);
}
