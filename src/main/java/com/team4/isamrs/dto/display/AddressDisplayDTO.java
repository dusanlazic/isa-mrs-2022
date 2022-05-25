package com.team4.isamrs.dto.display;

import lombok.Data;

@Data
public class AddressDisplayDTO {
    private String address;
    private String city;
    private String state;
    private String countryCode;
    private String postalCode;
    private String latitude;
    private String longitude;
}
