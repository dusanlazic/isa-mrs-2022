package com.team4.isamrs.dto.display;

import lombok.Data;

@Data
public class OptionDisplayDTO implements DisplayDTO {
    private Long id;
    private String name;
    private String description;
    private Integer maxCount;
}
