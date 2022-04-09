package com.team4.isamrs.dto;

import com.team4.isamrs.validation.CountryCode;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class AddressFormDto {
    @NotBlank
    @Size(max=60)
    private String address;

    @NotBlank
    @Size(max=60)
    private String city;

    @NotBlank
    @Size(max=60)
    private String state;

    @NotBlank
    @CountryCode
    private String countryCode;

    @NotBlank
    @Size(max=30)
    private String postalCode;

    @DecimalMin(value="-90")
    @DecimalMax(value="90")
    private String latitude;

    @DecimalMin(value="-180")
    @DecimalMax(value="180")
    private String longitude;
}
