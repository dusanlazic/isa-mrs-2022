package com.team4.isamrs.dto.display;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team4.isamrs.model.user.Advertiser;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class BoatAdDisplayDTO implements DisplayDTO {
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
    @JsonFormat(pattern="HH:mm")
    private LocalTime CheckOutTime;
    @JsonFormat(pattern="HH:mm")
    private LocalTime CheckInTime;
    private String boatType;
    private String boatLength;
    private String engineNumber;
    private String enginePower;
    private String boatSpeed;
    private Set<FishingEquipmentDisplayDTO> fishingEquipment = new HashSet<>();
    private Set<NavigationalEquipmentDisplayDTO> navigationalEquipment = new HashSet<>();
    private Integer capacity;
    private BigDecimal cancellationFee;
    private BigDecimal pricePerDay;
}
