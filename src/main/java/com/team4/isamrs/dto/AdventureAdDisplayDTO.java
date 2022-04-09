package com.team4.isamrs.dto;

import com.team4.isamrs.model.entity.advertisement.Address;
import com.team4.isamrs.model.entity.advertisement.Option;
import com.team4.isamrs.model.entity.user.Advertiser;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AdventureAdDisplayDTO {
    private Long id;
    private Advertiser advertiser;
    private String title;
    private Address address;
    private String description;
    private String pricingDescription;
    private LocalDateTime availableAfter;
    private LocalDateTime availableUntil;
    private String rules;
    private String currency;
    private Set<String> tags;
    private Set<String> photos;
    private Set<Option> options;
}
