package com.team4.isamrs.dto.updation;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequestResponseDTO {
    @NotNull
    private Boolean approve;

    @Size(max=60)
    private String rejectionReason;
}
