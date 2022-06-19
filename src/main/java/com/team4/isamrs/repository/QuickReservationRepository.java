package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.reservation.QuickReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface QuickReservationRepository extends JpaRepository<QuickReservation, Long> {

    @Query("SELECT qr FROM QuickReservation qr WHERE " +
            "qr.advertisement = :advertisement AND " +
            "qr.validAfter <= :now AND " +
            "qr.validUntil >= :now AND " +
            "qr.reservation IS NULL")
    Collection<QuickReservation> findActiveUntakenQuickReservations(
            Advertisement advertisement,
            LocalDateTime now);

    @Query("SELECT qr FROM QuickReservation qr WHERE " +
            "qr.advertisement = :advertisement AND " +
            "qr.validUntil > :now AND " +
            "qr.reservation IS NULL")
    Collection<QuickReservation> findUnexpiredUntakenQuickReservations(Advertisement advertisement, LocalDateTime now);
}
