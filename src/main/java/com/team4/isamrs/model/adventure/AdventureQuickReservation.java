package com.team4.isamrs.model.adventure;

import com.team4.isamrs.model.advertisement.Address;
import com.team4.isamrs.model.reservation.QuickReservation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AdventureQuickReservation extends QuickReservation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resort_ad_id")
    private AdventureAd advertisement;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "taken", nullable = false)
    private Boolean taken;
}
