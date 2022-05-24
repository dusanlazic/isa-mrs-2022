package com.team4.isamrs.dto.display;

import lombok.Data;

@Data
public class AdventureAdSimpleDisplayDTO {
    private Long id;
    private String title;
    private PhotoBriefDisplayDTO photo;
    private String description;
}
