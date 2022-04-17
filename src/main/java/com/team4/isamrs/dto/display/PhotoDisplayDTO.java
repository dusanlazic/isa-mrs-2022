package com.team4.isamrs.dto.display;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PhotoDisplayDTO implements DisplayDTO {
    /*
    Placeholder for testing relationships.
    Will be extended with some photo metadata when photo upload is implemented.
     */
    private String uri;
}
