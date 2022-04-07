package com.team4.isamrs.model.entity.adventure;

import com.team4.isamrs.model.entity.advertisement.Address;
import com.team4.isamrs.model.entity.reservation.QuickReservation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AdventureQuickReservation extends QuickReservation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resort_ad_id")
    private AdventureAd advertisement;

    @OneToOne
    private AdventureReservation associatedReservation;

    @Column
    private LocalDateTime startDateTime;

    @Column
    private LocalDateTime endDateTime;

    @Column
    private Integer capacity;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}
