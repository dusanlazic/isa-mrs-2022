package com.team4.isamrs.model.loyalty;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class LoyaltyProgramCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "targeted_account_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TargetedAccountType targetedAccountType;

    @Column(name = "points_lower_bound", nullable = false)
    private Integer pointsLowerBound;

    @Column(name = "points_upper_bound", nullable = false)
    private Integer pointsUpperBound;

    @Column(name = "multiply", nullable = false)
    private BigDecimal multiply;

    public LoyaltyProgramCategory() {

    }

    public LoyaltyProgramCategory(String title, TargetedAccountType targetedAccountType, Integer pointsLowerBound, Integer pointsUpperBound, BigDecimal multiply) {
        this.title = title;
        this.targetedAccountType = targetedAccountType;
        this.pointsLowerBound = pointsLowerBound;
        this.pointsUpperBound = pointsUpperBound;
        this.multiply = multiply;
    }
}
