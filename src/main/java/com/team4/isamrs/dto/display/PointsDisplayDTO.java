package com.team4.isamrs.dto.display;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointsDisplayDTO implements DisplayDTO {
    private Integer points;
    private LoyaltyProgramCategoryBriefDisplayDTO category;
}
