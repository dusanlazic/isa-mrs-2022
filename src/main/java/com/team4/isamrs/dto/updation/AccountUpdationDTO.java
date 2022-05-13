package com.team4.isamrs.dto.updation;

import com.team4.isamrs.validation.CountryCode;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AccountUpdationDTO {
    @NotBlank
    @Size(min = 2, max = 20, message = "First name must be longer than 2, and shorter than 20 characters.")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 32, message = "Last name must be longer than 2, and shorter than 32 characters.")
    private String lastName;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    @CountryCode
    private String countryCode;

    @NotBlank
    @Pattern(regexp = "[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}")
    private String phoneNumber;
}
