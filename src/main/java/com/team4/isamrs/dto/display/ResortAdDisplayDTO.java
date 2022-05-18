package com.team4.isamrs.dto.display;

import com.team4.isamrs.model.advertisement.Address;
import com.team4.isamrs.model.user.Advertiser;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class ResortAdDisplayDTO implements DisplayDTO {
    private Long id;
    private Advertiser advertiser;
    private String title;
    private Address address;
    private String description;
    private String pricingDescription;
    private LocalDate availableAfter;
    private LocalDate availableUntil;
    private String rules;
    private String currency;
    private Set<String> tags;
    private Set<PhotoBriefDisplayDTO> photos;
    private Set<OptionDisplayDTO> options;
    private String numberOfBeds;
    private LocalTime checkOutTime;
    private LocalTime checkInTime;
    private Set<DailyPriceDisplayDTO> prices;
}
