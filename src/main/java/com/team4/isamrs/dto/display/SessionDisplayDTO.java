package com.team4.isamrs.dto.display;

import lombok.Data;

@Data
public class SessionDisplayDTO implements DisplayDTO {
    private Long id;
    private PhotoBriefDisplayDTO avatar;
    private String firstName;
    private String lastName;
    private String accountType;
}
