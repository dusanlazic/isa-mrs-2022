package com.team4.isamrs.dto.display;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class ResortAdDisplayDTO implements DisplayDTO {
    private Long id;
    private AccountDisplayDTO advertiser;
    private String title;
    private AddressDisplayDTO address;
    private String description;
    private String pricingDescription;
    private LocalDate availableAfter;
    private LocalDate availableUntil;
    private String rules;
    private String currency;
    private Set<String> tags;
    private Set<PhotoUploadDisplayDTO> photos;
    private Set<OptionDisplayDTO> options;
    private String numberOfBeds;
    @JsonFormat(pattern="HH:mm")
    private LocalTime checkOutTime;
    @JsonFormat(pattern="HH:mm")
    private LocalTime checkInTime;
    private BigDecimal pricePerPerson;
}
