package com.team4.isamrs.model.advertisement;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalTime;

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

    @Column(name = "price_per_day", nullable = false)
    private BigDecimal pricePerDay;
}
