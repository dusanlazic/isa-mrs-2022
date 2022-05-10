package com.team4.isamrs.dto.updation;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class HourlyPriceUpdationDTO {
    @NotNull
    @Positive
    private BigDecimal value;

    @NotNull
    @PositiveOrZero
    private Integer minHours;

    private Boolean delete;
}
