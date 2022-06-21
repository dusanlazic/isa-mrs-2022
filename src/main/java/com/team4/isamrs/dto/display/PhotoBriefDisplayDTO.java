package com.team4.isamrs.dto.display;

import lombok.Data;

import java.util.UUID;

@Data
public class PhotoBriefDisplayDTO implements DisplayDTO {
    private String uri;
    private UUID id;

    public PhotoBriefDisplayDTO(String uri) {
        this.uri = uri;
    }

    public PhotoBriefDisplayDTO() {
        // ModelMapper requires a non-private no-argument constructor
    }
}
