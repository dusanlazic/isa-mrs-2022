package com.team4.isamrs.model.entity.advertisement;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class DailyPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal value;

    @Column
    /*
    Price is applied when reservation length exceeds defined number of days.
    e.g. days < 5 => $100/d,
         days >= 5 => $90/d,
         days >= 7 => $70/d
     */
    private Integer minDays;
}
