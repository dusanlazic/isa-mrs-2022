package com.team4.isamrs.dto.updation;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
public class LoyaltyProgramSettingsUpdationDTO {
    @PositiveOrZero
    private Integer clientScorePerReservation;

    @PositiveOrZero
    private Integer advertiserScorePerReservation;

}
