package com.team4.isamrs.model.entity.boat;

import com.team4.isamrs.model.entity.reservation.Reservation;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BoatReservation extends Reservation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resort_ad_id")
    private BoatAd advertisement;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;
}
