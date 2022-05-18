package com.team4.isamrs.dto.updation;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AvailabilityPeriodUpdationDTO {

    @NotNull
    private LocalDate availableAfter;

    @NotNull
    private LocalDate availableUntil;
}
