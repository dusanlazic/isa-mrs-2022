package com.team4.isamrs.model.adventure;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.HourlyPrice;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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

    public void addFishingEquipment(FishingEquipment singleFishingEquipment) {
        fishingEquipment.add(singleFishingEquipment);
        singleFishingEquipment.getAdvertisements().add(this);
    }

    public void removeFishingEquipment(FishingEquipment singleFishingEquipment) {
        fishingEquipment.remove(singleFishingEquipment);
        singleFishingEquipment.getAdvertisements().remove(this);
    }
}
