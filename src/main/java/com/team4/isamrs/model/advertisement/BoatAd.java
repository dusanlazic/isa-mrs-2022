package com.team4.isamrs.model.advertisement;

import com.team4.isamrs.model.reservation.Reservation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class BoatAd extends Advertisement {
    @Column(name = "check_out_time", nullable = false)
    private LocalTime checkOutTime; // e.g. 10:00

    @Column(name = "check_in_time", nullable = false)
    private LocalTime checkInTime; // e.g. 13:00

    @Column(name = "boat_type")
    private String boatType;

    @Column(name = "boat_length")
    private String boatLength;

    @Column(name = "engine_number")
    private String engineNumber;

    @Column(name = "engine_power")
    private String enginePower;

    @Column(name = "boat_speed")
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

    @Column(name = "cancellation_fee", nullable = false)
    private BigDecimal cancellationFee;

    @Column(name = "price_per_day", nullable = false)
    private BigDecimal pricePerDay;

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<>();

    public void addFishingEquipment(FishingEquipment singleFishingEquipment) {
        fishingEquipment.add(singleFishingEquipment);
        singleFishingEquipment.getAdvertisements().add(this);
    }

    public void addNavigationalEquipment(NavigationalEquipment singleNavigationalEquipment) {
        navigationalEquipment.add(singleNavigationalEquipment);
        singleNavigationalEquipment.getAdvertisements().add(this);
    }
}
