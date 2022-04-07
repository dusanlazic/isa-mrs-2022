package com.team4.isamrs.model.entity.resort;

import com.team4.isamrs.model.entity.reservation.QuickReservation;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ResortQuickReservation extends QuickReservation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resort_ad_id")
    private ResortAd advertisement;

    @OneToOne
    private ResortReservation associatedReservation;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private Integer capacity;
}
