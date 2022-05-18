package com.team4.isamrs.dto.updation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailabilityPeriodUpdationDTO {

    private LocalDate availableAfter;

    private LocalDate availableUntil;
}
