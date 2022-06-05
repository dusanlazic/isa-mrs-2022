package com.team4.isamrs.dto.creation;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ReviewCreationDTO {
    @NotNull
    @DecimalMin(value = "1.0", inclusive = true)
    @DecimalMax(value = "5.0", inclusive = true)
    private Double rating;

    @Size(max = 100)
    private String comment;
}
