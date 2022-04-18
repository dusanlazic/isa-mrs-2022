package com.team4.isamrs.dto.creation;

import com.team4.isamrs.validation.CountryCode;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class AccountCreationDTO {

    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20, message = "First name must be longer than 2, and shorter than 20 characters.")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 32, message = "Last name must be longer than 2, and shorter than 32 characters.")
    private String lastName;

    @NotBlank
    @Email(message = "Email should be valid")
    private String emailAddress;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    @CountryCode
    private String countryCode;

    @NotBlank
    @Pattern(regexp = "[0-9]*")
    private String phoneNumber;
}
