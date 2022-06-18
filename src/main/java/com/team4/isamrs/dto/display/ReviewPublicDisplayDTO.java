package com.team4.isamrs.dto.display;

import lombok.Data;

import javax.mail.Session;
import java.time.LocalDateTime;

@Data
public class ReviewPublicDisplayDTO {
    private Long id;
    private LocalDateTime createdAt;
    private AdvertisementSimpleDisplayDTO advertisement;
    private SessionDisplayDTO advertiser;
    private CustomerPublicDisplayDTO customer;
    private Integer rating;
    private String comment;
}
