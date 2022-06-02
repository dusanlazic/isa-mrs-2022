package com.team4.isamrs.dto.display;

import com.team4.isamrs.model.loyalty.TargetedAccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoyaltyProgramCategoryDetailedDisplayDTO implements DisplayDTO {
    private Long id;
    private String title;
    private String color;
    private TargetedAccountType targetedAccountType;
    private Integer pointsLowerBound;
    private Integer pointsUpperBound;
    private BigDecimal multiply;
}
