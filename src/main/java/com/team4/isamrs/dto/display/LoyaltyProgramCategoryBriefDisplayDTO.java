package com.team4.isamrs.dto.display;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoyaltyProgramCategoryBriefDisplayDTO implements DisplayDTO {
    private String title;
    private String color;
    private Integer pointsLowerBound;
    private Integer pointsUpperBound;
    private BigDecimal multiply;
}
