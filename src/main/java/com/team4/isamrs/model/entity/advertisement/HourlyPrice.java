package com.team4.isamrs.model.entity.advertisement;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class HourlyPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal value;

    @Column
    /*
    Price is applied when reservation length exceeds defined number of hours.
    e.g. hours < 5 => $10/h,
         hours >= 5 => $9/h,
         hours >= 7 => $7/h
     */
    private Integer minHours;
}
