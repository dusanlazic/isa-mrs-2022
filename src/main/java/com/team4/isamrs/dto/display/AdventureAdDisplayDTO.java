package com.team4.isamrs.dto.display;

import com.team4.isamrs.model.advertisement.Address;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class AdventureAdDisplayDTO implements DisplayDTO {
    private AccountDisplayDTO advertiser;
    private String title;
    private Address address;
    private String description;
    private String instructorBio;
    private BigDecimal cancellationFee;
    private String pricingDescription;
    private LocalDate availableAfter;
    private LocalDate availableUntil;
    private String rules;
    private String currency;
    private Integer capacity;
    private Set<String> tags;
    private Set<PhotoUploadDisplayDTO> photos;
    private List<OptionDisplayDTO> options;
    private Set<FishingEquipmentDisplayDTO> fishingEquipment;
    private List<HourlyPriceDisplayDTO> prices;
}
