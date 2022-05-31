package com.team4.isamrs.dto.display;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoyaltyProgramSettingsDisplayDTO implements DisplayDTO {
    private String clientScorePerReservation;
    private String advertiserScorePerReservation;
}
