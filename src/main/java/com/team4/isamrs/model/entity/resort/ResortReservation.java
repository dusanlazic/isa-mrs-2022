package com.team4.isamrs.model.entity.resort;

import com.team4.isamrs.model.entity.reservation.Reservation;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ResortReservation extends Reservation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resort_ad_id")
    private ResortAd advertisement;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;
}
