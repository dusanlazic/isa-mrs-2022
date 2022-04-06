package com.team4.isamrs.model.entity;

import com.team4.isamrs.model.enumeration.CategoryType;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class LoyaltyProgramCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private CategoryType type;

    @Column
    private Integer pointsRequired;

    @Column
    private BigDecimal discount;

    @Column
    private BigDecimal bonus;
}
