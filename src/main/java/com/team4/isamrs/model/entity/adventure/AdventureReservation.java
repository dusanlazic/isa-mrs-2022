package com.team4.isamrs.model.entity.adventure;

import com.team4.isamrs.model.entity.reservation.Reservation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AdventureReservation extends Reservation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resort_ad_id")
    private AdventureAd advertisement;

    @Column
    private LocalDateTime startDateTime;

    @Column
    private LocalDateTime endDateTime;
}
