package com.team4.isamrs.dto.display;

import lombok.Data;

@Data
public class AdvertisementSimpleDisplayDTO {
    private Long id;
    private String title;
    private PhotoBriefDisplayDTO photo;
    private String description;
    private String currency;
    private AddressSimpleDisplayDTO address;
    private SessionDisplayDTO advertiser;
    private String advertisementType;
}
