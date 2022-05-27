package com.team4.isamrs.model.advertisement;

import com.team4.isamrs.model.reservation.Reservation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashSet;
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
    private LocalTime checkOutTime; // e.g. 10:00

    @Column(name = "check_in_time", nullable = false)
    private LocalTime checkInTime; // e.g. 13:00

    @Column(name = "price_per_day", nullable = false)
    private BigDecimal pricePerDay;
}
