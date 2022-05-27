package com.team4.isamrs.model.advertisement;

import com.team4.isamrs.model.reservation.Reservation;
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

    /*
    e.g. Fishing rods, Baits, Net
     */
    @ManyToMany(mappedBy = "advertisements", fetch = FetchType.LAZY)
    private Set<FishingEquipment> fishingEquipment = new HashSet<>();

    @Column(name = "cancellation_fee", nullable = false)
    private BigDecimal cancellationFee;

    @Column(name = "price_per_person", nullable = false)
    private BigDecimal pricePerPerson;

    public void addFishingEquipment(FishingEquipment singleFishingEquipment) {
        fishingEquipment.add(singleFishingEquipment);
        singleFishingEquipment.getAdvertisements().add(this);
    }

    public void removeFishingEquipment(FishingEquipment singleFishingEquipment) {
        fishingEquipment.remove(singleFishingEquipment);
        singleFishingEquipment.getAdvertisements().remove(this);
    }
}
