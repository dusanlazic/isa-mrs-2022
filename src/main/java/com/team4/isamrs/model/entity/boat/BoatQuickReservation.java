package com.team4.isamrs.model.entity.boat;

import com.team4.isamrs.model.entity.reservation.QuickReservation;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BoatQuickReservation extends QuickReservation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resort_ad_id")
    private BoatAd advertisement;

    @OneToOne
    private BoatReservation associatedReservation;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private Integer capacity;
}
