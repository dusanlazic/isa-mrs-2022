package com.team4.isamrs.dto.creation;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
public class ReviewCreationDTO {
    @NotNull
    @Range(min = 1, max = 5)
    private Integer rating;

    @Size(max = 100)
    private String comment;
}
