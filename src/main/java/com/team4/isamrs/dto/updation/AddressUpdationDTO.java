package com.team4.isamrs.dto.updation;

import com.team4.isamrs.validation.CountryCode;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddressUpdationDTO {
    /* Note:
    No different from AddressCreationDTO.

    Data changes can be restricted by removing fields from this class.
    If no restrictions should be applied, reusing AddressCreationDTO
    class and removing this one is fine.
     */

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
