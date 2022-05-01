package com.team4.isamrs.dto.display;

import com.team4.isamrs.model.advertisement.Address;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AdventureAdDisplayDTO implements DisplayDTO {
    private Long id;
    private AccountDisplayDTO advertiser;
    private String title;
    private Address address;
    private String description;
    private String pricingDescription;
    private LocalDateTime availableAfter;
    private LocalDateTime availableUntil;
    private String rules;
    private String currency;
    private Set<String> tags;
    private Set<PhotoBriefDisplayDTO> photos;
    private Set<OptionDisplayDTO> options;
    private Set<FishingEquipmentDisplayDTO> fishingEquipment;
    private Set<HourlyPriceDisplayDTO> prices;
}
