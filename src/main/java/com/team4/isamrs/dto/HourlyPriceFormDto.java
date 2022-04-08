package com.team4.isamrs.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HourlyPriceFormDto {
    private BigDecimal value;
    private Integer minHours;
}
