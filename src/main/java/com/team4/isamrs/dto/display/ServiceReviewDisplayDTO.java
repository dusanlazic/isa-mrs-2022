package com.team4.isamrs.dto.display;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceReviewDisplayDTO {
    private Long id;
    private LocalDateTime createdAt;
    private AdvertisementDisplayDTO advertisement;
    private CustomerDisplayDTO customer;
    private Double rating;
    private String comment;
    private ApprovalStatus approvalStatus;
}
