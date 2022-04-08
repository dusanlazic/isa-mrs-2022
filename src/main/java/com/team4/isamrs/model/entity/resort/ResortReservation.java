package com.team4.isamrs.model.entity.resort;

import com.team4.isamrs.model.entity.reservation.Reservation;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ResortReservation extends Reservation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resort_ad_id")
    private ResortAd advertisement;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
}
