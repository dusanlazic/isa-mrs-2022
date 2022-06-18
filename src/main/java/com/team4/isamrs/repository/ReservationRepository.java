package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.reservation.ReservationReport;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    Set<Reservation> findReservationsByStartDateTimeBeforeOrEndDateTimeAfterAndCancelledIsFalse(LocalDateTime from, LocalDateTime to);
    Set<Reservation> findReservationsByStartDateTimeBeforeAndEndDateTimeAfterAndCancelledIsFalse(LocalDateTime to, LocalDateTime from);
    Set<Reservation> findReservationsByEndDateTimeAfterAndCancelledIsFalse(LocalDateTime to);
    Set<Reservation> findReservationsByStartDateTimeBeforeAndCancelledIsFalse(LocalDateTime from);
    @Query(value = "SELECT r FROM Reservation r JOIN r.advertisement a WHERE :adId = a.id AND " +
            "(:startDate < r.startDateTime AND :endDate > r.startDateTime) OR " +
            "(:startDate <= r.endDateTime AND :endDate > r.endDateTime) OR " +
            "(:startDate >= r.startDateTime AND :endDate <= r.endDateTime)")
    List<Reservation> getResortReservationsForRange(
            @Param("adId") Long adId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    Boolean existsByAdvertisementEqualsAndCustomerEqualsAndCancelledIsFalseAndEndDateTimeBefore(Advertisement advertisement, Customer customer, LocalDateTime endDateTime);


    @Query(value = "SELECT r FROM Reservation r WHERE r.cancelled = false AND r.advertisement.advertiser = ?1")
    Page<Reservation> findReservationsForAdvertiser(Advertiser advertiser, Pageable pageable);

}
