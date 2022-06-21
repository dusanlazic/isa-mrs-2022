package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    Set<Reservation> findReservationsByStartDateTimeBeforeOrEndDateTimeAfterAndCancelledIsFalse(LocalDateTime from, LocalDateTime to);
    Set<Reservation> findReservationsByStartDateTimeBeforeAndEndDateTimeAfterAndCancelledIsFalse(LocalDateTime to, LocalDateTime from);
    Set<Reservation> findReservationsByEndDateTimeAfterAndCancelledIsFalse(LocalDateTime to);
    Set<Reservation> findReservationsByStartDateTimeBeforeAndCancelledIsFalse(LocalDateTime from);

    Page<Reservation> findReservationsByCustomerEqualsAndStartDateTimeBefore(Customer customer, LocalDateTime date, Pageable pageable);
    Page<Reservation> findReservationsByCustomerEqualsAndStartDateTimeAfter(Customer customer, LocalDateTime date, Pageable pageable);

    @Query(value = "SELECT r FROM Reservation r JOIN r.advertisement a WHERE :adId = a.id AND " +
            "(:startDate < r.startDateTime AND :endDate > r.startDateTime) OR " +
            "(:startDate <= r.endDateTime AND :endDate > r.endDateTime) OR " +
            "(:startDate >= r.startDateTime AND :endDate <= r.endDateTime)")
    List<Reservation> getResortReservationsForRange(
            @Param("adId") Long adId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    Boolean existsByAdvertisementEqualsAndCustomerEqualsAndCancelledIsFalseAndEndDateTimeBefore(Advertisement advertisement, Customer customer, LocalDateTime endDateTime);

    Set<Reservation> findByAdvertisementEqualsAndCustomerEqualsAndCancelledIsTrue
            (Advertisement advertisement, Customer customer);

    @Query(value = "SELECT r FROM Reservation r WHERE r.cancelled = false AND r.advertisement.advertiser = ?1")
    Page<Reservation> findReservationsForAdvertiser(Advertiser advertiser, Pageable pageable);

    @Query(value = "SELECT r FROM Reservation r WHERE r.cancelled = false AND r.id = :reservationId AND r.advertisement.id = :advertisementId AND r.advertisement.advertiser.id = :advertiserId AND r.customer.id = :custId AND r.startDateTime < :now AND r.endDateTime > :now")
    Optional<Reservation> findByAdvertisementIdAndAdvertiserIdAndClientIdAndDate(@Param("reservationId") Long reservationId, @Param("advertisementId") Long advertisementId, @Param("advertiserId") Long advertiserId, @Param("custId") Long customerId, @Param("now") LocalDateTime now);

    @Query(value = "SELECT r FROM Reservation r WHERE r.cancelled = false AND r.advertisement.advertiser = ?1 AND r.startDateTime < ?2 AND r.endDateTime > ?2")
    Page<Reservation> findActiveReservationsForAdvertiser(Advertiser advertiser, LocalDateTime now, Pageable pageable);

    @Query(value = "SELECT r FROM Reservation r LEFT JOIN ReservationReport rep ON rep.reservation = r WHERE r.cancelled = false AND rep IS NULL AND r.advertisement.advertiser = ?1 AND r.endDateTime < ?2")
    Page<Reservation> findReservationsForAdvertiserWithPendingReport(Advertiser advertiser, LocalDateTime now, Pageable pageable);
}
