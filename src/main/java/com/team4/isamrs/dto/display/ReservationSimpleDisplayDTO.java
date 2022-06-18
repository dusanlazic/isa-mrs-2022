package com.team4.isamrs.dto.display;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReservationSimpleDisplayDTO {
    private Long id;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime startDateTime;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime endDateTime;
    private AdvertisementSimpleDisplayDTO advertisement;
    private CustomerSimpleDisplayDTO customer;
    private BigDecimal calculatedPrice;
    private boolean cancelled;
    private boolean canBeReviewed;
}
