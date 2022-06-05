package com.team4.isamrs.dto.creation;

import com.team4.isamrs.validation.CountryCode;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddressCreationDTO {
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

    @NotBlank
    @DecimalMin(value="-90")
    @DecimalMax(value="90")
    private String latitude;

    @NotBlank
    @DecimalMin(value="-180")
    @DecimalMax(value="180")
    private String longitude;
}
