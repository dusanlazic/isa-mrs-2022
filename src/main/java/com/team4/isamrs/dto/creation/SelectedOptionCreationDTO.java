package com.team4.isamrs.dto.creation;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SelectedOptionCreationDTO {
    @NotNull
    private Long optionId;

    @NotNull
    private Integer count;
}
