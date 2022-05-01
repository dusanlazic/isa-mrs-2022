package com.team4.isamrs.dto.display;

import lombok.Data;

import java.util.UUID;

@Data
public class PhotoUploadDisplayDTO implements DisplayDTO {
    private String uri;
    private UUID id;
}
