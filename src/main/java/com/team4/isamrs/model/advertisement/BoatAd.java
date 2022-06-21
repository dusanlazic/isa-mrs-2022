package com.team4.isamrs.model.advertisement;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class BoatAd extends Advertisement {
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

    public void addFishingEquipment(FishingEquipment singleFishingEquipment) {
        fishingEquipment.add(singleFishingEquipment);
        singleFishingEquipment.getAdvertisements().add(this);
    }

    public void addNavigationalEquipment(NavigationalEquipment singleNavigationalEquipment) {
        navigationalEquipment.add(singleNavigationalEquipment);
        singleNavigationalEquipment.getAdvertisements().add(this);
    }
}
