package com.team4.isamrs.model.entity.resort;

import com.team4.isamrs.model.entity.advertisement.Advertisement;
import com.team4.isamrs.model.entity.advertisement.DailyPrice;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ResortAd extends Advertisement {
    /*
    Number of beds for each room separated by commas.
    e.g. 3,1,0,1 => 4 rooms, 5 beds total: three in the first, one in the second, etc.
     */
    @Column
    private String numberOfBeds;

    @Column
    private LocalTime CheckOutTime; // e.g. 10:00

    @Column
    private LocalTime CheckInTime; // e.g. 13:00

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DailyPrice> prices = new HashSet<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<ResortReservation> reservations = new HashSet<>();
}
