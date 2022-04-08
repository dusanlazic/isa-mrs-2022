package com.team4.isamrs.model.entity.loyalty;

import com.team4.isamrs.model.enumeration.CategoryType;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class LoyaltyProgramCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CategoryType type;

    @Column(name = "points_required", nullable = false)
    private Integer pointsRequired;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;

    @Column(name = "bonus", nullable = false)
    private BigDecimal bonus;
}
