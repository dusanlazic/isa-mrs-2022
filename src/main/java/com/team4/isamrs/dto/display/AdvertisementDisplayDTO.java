package com.team4.isamrs.dto.display;

import com.team4.isamrs.model.user.Advertiser;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class AdvertisementDisplayDTO implements DisplayDTO {
    private Long id;
    private Advertiser advertiser;
    private String title;
    private AddressDisplayDTO address;
    private String description;
    private String pricingDescription;
    private LocalDate availableAfter;
    private LocalDate availableUntil;
    private String rules;
    private String currency;
    private Set<String> tags;
    private Set<PhotoBriefDisplayDTO> photos;
    private Set<OptionDisplayDTO> options;
}
