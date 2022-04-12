package com.team4.isamrs.dto.display;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HourlyPriceDisplayDTO implements DisplayDTO {
    private Long id;
    private BigDecimal value;
    private Integer minHours;
}
