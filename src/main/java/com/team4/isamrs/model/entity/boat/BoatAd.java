package com.team4.isamrs.model.entity.boat;

import com.team4.isamrs.model.entity.adventure.FishingEquipment;
import com.team4.isamrs.model.entity.advertisement.Advertisement;
import com.team4.isamrs.model.entity.advertisement.DailyPrice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BoatAd extends Advertisement {
    @Column
    private LocalTime CheckOutTime; // e.g. 10:00

    @Column
    private LocalTime CheckInTime; // e.g. 13:00

    @Column
    private String boatType;

    @Column
    private String boatLength;

    @Column
    private String engineNumber;

    @Column
    private String enginePower;

    @Column
    private String boatSpeed;

    /*
    e.g. Fishing rods, Baits, Net
     */
    @ManyToMany(mappedBy = "advertisements", fetch = FetchType.LAZY)
    private Set<FishingEquipment> fishingEquipment = new HashSet<>();

    /*
    e.g. "GPS, Radar, VHF radio, Fish-finder"
     */
    @ManyToMany(mappedBy = "advertisements", fetch = FetchType.LAZY)
    private Set<NavigationalEquipment> navigationalEquipment = new HashSet<>();

    @Column
    private Integer capacity;

    @Column
    private BigDecimal cancellationFee;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DailyPrice> prices = new HashSet<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<BoatReservation> reservations = new HashSet<>();
}
