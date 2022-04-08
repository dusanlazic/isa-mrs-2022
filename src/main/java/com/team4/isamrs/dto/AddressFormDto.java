package com.team4.isamrs.dto;

import lombok.Data;

@Data
public class AddressFormDto {
    private String address;
    private String city;
    private String state;
    private String countryCode;
    private String postalCode;
    private String latitude;
    private String longitude;
}
