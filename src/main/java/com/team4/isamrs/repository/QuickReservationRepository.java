package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.reservation.QuickReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface QuickReservationRepository extends JpaRepository<QuickReservation, Long> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT qr FROM QuickReservation qr WHERE qr.id = ?1")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Optional<QuickReservation> lockGetById(Long id);

    @Query("SELECT qr FROM QuickReservation qr WHERE " +
            "qr.advertisement = :advertisement AND " +
            "qr.validAfter <= :now AND " +
            "qr.validUntil >= :now")
    Collection<QuickReservation> findActiveUntakenQuickReservations(
            Advertisement advertisement,
            LocalDateTime now);

    @Query("SELECT qr FROM QuickReservation qr WHERE " +
            "qr.advertisement = :advertisement AND " +
            "qr.validUntil > :now AND " +
            "qr.reservation IS NULL")
    Collection<QuickReservation> findUnexpiredUntakenQuickReservations(Advertisement advertisement, LocalDateTime now);
}
