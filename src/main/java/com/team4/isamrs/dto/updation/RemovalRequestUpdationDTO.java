package com.team4.isamrs.dto.updation;

import com.team4.isamrs.dto.display.DisplayDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RemovalRequestUpdationDTO implements DisplayDTO {
    @NotNull
    private Boolean approve;

    @Size(max=60)
    private String rejectionReason;
}
