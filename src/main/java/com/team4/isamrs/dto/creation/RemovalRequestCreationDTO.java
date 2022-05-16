package com.team4.isamrs.dto.creation;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RemovalRequestCreationDTO {
    @NotBlank
    private String explanation;
}
