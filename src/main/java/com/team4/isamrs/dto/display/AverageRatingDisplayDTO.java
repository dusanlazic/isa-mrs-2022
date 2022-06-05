package com.team4.isamrs.dto.display;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AverageRatingDisplayDTO implements DisplayDTO {
    private Double average;
}
