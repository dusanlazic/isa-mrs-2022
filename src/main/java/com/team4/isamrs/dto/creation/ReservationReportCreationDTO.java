package com.team4.isamrs.dto.creation;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReservationReportCreationDTO {
    @NotNull
    private String comment;

    @NotNull
    private Boolean penaltyRequested;

    @NotNull
    private Boolean customerWasLate;
}
