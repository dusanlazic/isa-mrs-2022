package com.team4.isamrs.dto.display;

import lombok.Data;

@Data
public class ResortAdSimpleDisplayDTO implements DisplayDTO {
    private Long id;
    private String title;
    private PhotoBriefDisplayDTO photo;
    private String description;
}
