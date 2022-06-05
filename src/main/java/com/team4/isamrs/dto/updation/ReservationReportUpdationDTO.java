package com.team4.isamrs.dto.updation;

import com.team4.isamrs.dto.display.DisplayDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReservationReportUpdationDTO implements DisplayDTO {
    @NotNull
    private Boolean approve;
}
