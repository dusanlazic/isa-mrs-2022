package com.team4.isamrs.model.entity.adventure;

import com.team4.isamrs.model.entity.advertisement.Advertisement;
import com.team4.isamrs.model.entity.advertisement.HourlyPrice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AdventureAd extends Advertisement {
    @Column(name = "instructor_bio", nullable = false)
    private String instructorBio;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    /*
    e.g. Fishing rods, Baits, Net
     */
    @ManyToMany(mappedBy = "advertisements", fetch = FetchType.LAZY)
    private Set<FishingEquipment> fishingEquipment = new HashSet<>();

    @Column(name = "cancellation_fee", nullable = false)
    private BigDecimal cancellationFee;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<HourlyPrice> prices = new HashSet<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<AdventureReservation> reservations = new HashSet<>();
}