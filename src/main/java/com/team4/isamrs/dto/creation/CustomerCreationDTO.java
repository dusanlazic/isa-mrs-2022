package com.team4.isamrs.dto.creation;

import com.team4.isamrs.validation.CountryCode;
import com.team4.isamrs.validation.Email;
import com.team4.isamrs.validation.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class CustomerCreationDTO {
    @NotBlank
    @Size(min = 2, max = 20, message = "First name must be longer than 2, and shorter than 20 characters.")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 32, message = "Last name must be longer than 2, and shorter than 32 characters.")
    private String lastName;

    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    @CountryCode
    private String countryCode;

    @NotBlank
    @Pattern(regexp = "[+]?[(]?\\d{3}[)]?[-\\s.]?\\d{3}[-\\s.]?\\d{4,6}")
    private String phoneNumber;

    @NotBlank
    @Size(min = 8, message = "Password must be longer than 8 characters.")
    private String password;

    @NotBlank
    private String passwordConfirmation;
}
