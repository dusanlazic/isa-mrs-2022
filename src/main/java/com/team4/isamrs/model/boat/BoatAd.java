package com.team4.isamrs.model.boat;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.DailyPrice;
import com.team4.isamrs.model.adventure.FishingEquipment;
import com.team4.isamrs.model.user.Advertiser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class BoatAd extends Advertisement {
    @Column(name = "check_out_time", nullable = false)
    private LocalTime CheckOutTime; // e.g. 10:00

    @Column(name = "check_in_time", nullable = false)
    private LocalTime CheckInTime; // e.g. 13:00

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

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "cancellation_fee", nullable = false)
    private BigDecimal cancellationFee;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DailyPrice> prices = new ArrayList<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<BoatReservation> reservations = new HashSet<>();

    public void addFishingEquipment(FishingEquipment singleFishingEquipment) {
        fishingEquipment.add(singleFishingEquipment);
        singleFishingEquipment.getAdvertisements().add(this);
    }

    public void addNavigationalEquipment(NavigationalEquipment singleNavigationalEquipment) {
        navigationalEquipment.add(singleNavigationalEquipment);
        singleNavigationalEquipment.getAdvertisements().add(this);
    }

    public void addDailyPrice(DailyPrice dailyPrice) {
        prices.add(dailyPrice);
    }

    public void removeDailyPrice(DailyPrice dailyPrice) { prices.remove(dailyPrice); }
}
