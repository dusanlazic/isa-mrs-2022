package com.team4.isamrs.dto.creation;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ComplaintCreationDTO {
    @NotNull
    private String comment;
}
