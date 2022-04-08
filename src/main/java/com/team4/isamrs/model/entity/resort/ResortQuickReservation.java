package com.team4.isamrs.model.entity.resort;

import com.team4.isamrs.model.entity.reservation.QuickReservation;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ResortQuickReservation extends QuickReservation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resort_ad_id")
    private ResortAd advertisement;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "taken", nullable = false)
    private Boolean taken;
}
