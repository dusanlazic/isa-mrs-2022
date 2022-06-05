package com.team4.isamrs.dto.updation;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ComplaintResponseDTO {
    @NotBlank
    private String messageToCustomer;

    @NotBlank
    private String messageToAdvertiser;
}
