package com.team4.isamrs.model.resort;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.DailyPrice;
import com.team4.isamrs.model.advertisement.HourlyPrice;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class ResortAd extends Advertisement {
    /*
    Number of beds for each room separated by commas.
    e.g. 3,1,0,1 => 4 rooms, 5 beds total: three in the first, one in the second, etc.
     */
    @Column(name = "number_of_beds", nullable = false)
    private String numberOfBeds;

    @Column(name = "check_out_time", nullable = false)
    private LocalTime CheckOutTime; // e.g. 10:00

    @Column(name = "check_in_time", nullable = false)
    private LocalTime CheckInTime; // e.g. 13:00

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DailyPrice> prices = new ArrayList<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<ResortReservation> reservations = new HashSet<>();

    public void addDailyPrice(DailyPrice dailyPrice) {
        prices.add(dailyPrice);
    }

    public void removeDailyPrice(DailyPrice dailyPrice) {
        prices.remove(dailyPrice);
    }
}
