package com.team4.isamrs.dto.display;

import lombok.Data;

@Data
public class SelectedOptionDisplayDTO {
    private Long id;
    private OptionDisplayDTO option;
    private Integer count;
}
