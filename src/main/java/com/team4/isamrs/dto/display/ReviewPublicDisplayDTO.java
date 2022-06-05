package com.team4.isamrs.dto.display;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewPublicDisplayDTO {
    private Long id;
    private LocalDateTime createdAt;
    private CustomerPublicDisplayDTO customer;
    private Double rating;
    private String comment;
}
