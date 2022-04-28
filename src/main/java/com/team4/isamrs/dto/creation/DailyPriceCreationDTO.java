package com.team4.isamrs.dto.creation;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class DailyPriceCreationDTO {
    @NotNull
    @Positive
    private BigDecimal value;

    @NotNull
    @PositiveOrZero
    private Integer minDays;
}