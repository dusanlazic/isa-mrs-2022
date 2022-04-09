package com.team4.isamrs.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class OptionCreationDTO {
    @NotBlank
    @Size(max=60)
    private String name;

    @Size(max=100)
    private String description;

    @NotNull
    @Positive
    private Integer maxCount;
}
