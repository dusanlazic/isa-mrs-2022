package com.team4.isamrs.dto.display;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReservationSimpleDisplayDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private AdvertisementSimpleDisplayDTO advertisement;
    private CustomerSimpleDisplayDTO customer;
    private BigDecimal calculatedPrice;
    private boolean cancelled;
    private boolean canBeReviewed;
}
