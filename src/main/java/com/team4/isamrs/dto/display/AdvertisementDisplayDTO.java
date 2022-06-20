package com.team4.isamrs.dto.display;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class AdvertisementDisplayDTO implements DisplayDTO {
    private Long id;
    private AccountDisplayDTO advertiser;
    private String title;
    private Integer capacity;
    private AddressDisplayDTO address;
    private String description;
    private String pricingDescription;
    private LocalDate availableAfter;
    private LocalDate availableUntil;
    private String rules;
    private String currency;
    private Set<String> tags;
    private List<PhotoBriefDisplayDTO> photos;
    private Set<OptionDisplayDTO> options;
}
