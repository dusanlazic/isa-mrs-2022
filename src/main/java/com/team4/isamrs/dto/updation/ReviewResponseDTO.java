package com.team4.isamrs.dto.updation;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReviewResponseDTO {
    @NotNull
    private Boolean approve;
}
