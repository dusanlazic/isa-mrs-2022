package com.team4.isamrs.dto.display;

import lombok.Data;

@Data
public class LoyaltyProgramCategoryBriefDisplayDTO implements DisplayDTO {
    private String title;
    private Integer pointsLowerBound;
    private Integer pointsUpperBound;
}
