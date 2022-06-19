package com.team4.isamrs.dto.display;

import com.team4.isamrs.model.enumeration.ResponseStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintDisplayDTO implements DisplayDTO {
    private Long id;
    private LocalDateTime createdAt;
    private AdvertisementDisplayDTO advertisement;
    private CustomerSimpleDisplayDTO customer;
    private String comment;
    private ResponseStatus responseStatus;
}