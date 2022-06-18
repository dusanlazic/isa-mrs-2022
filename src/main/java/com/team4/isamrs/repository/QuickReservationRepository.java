package com.team4.isamrs.repository;

import com.team4.isamrs.model.reservation.QuickReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuickReservationRepository extends JpaRepository<QuickReservation, Long> {

}
