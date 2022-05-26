package com.team4.isamrs.model.adventure;

import com.team4.isamrs.model.reservation.Reservation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AdventureReservation extends Reservation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adventure_ad_id")
    private AdventureAd advertisement;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;
}
