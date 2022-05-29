package com.team4.isamrs.dto.display;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdventureAdSimpleDisplayDTO {
    private Long id;
    private String title;
    private PhotoBriefDisplayDTO photo;
    private String description;
    private String currency;
    private BigDecimal pricePerPerson;
    private AddressSimpleDisplayDTO address;
}
