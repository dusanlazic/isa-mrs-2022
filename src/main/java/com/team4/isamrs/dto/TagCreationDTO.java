package com.team4.isamrs.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TagCreationDTO {
    @NotBlank
    @Size(min = 2, max = 40)
    private String name;
}
