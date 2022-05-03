package com.team4.isamrs.dto.creation;

import com.team4.isamrs.model.enumeration.AccountType;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.validation.CountryCode;
import com.team4.isamrs.validation.Email;
import com.team4.isamrs.validation.PasswordMatches;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.*;

@Data
@PasswordMatches
@Getter
public class RegistrationRequestCreationDTO {
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
    private String password;

    @NotBlank
    private String passwordConfirmation;

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

    @NotBlank
    @Size(min = 20, max = 500, message = "Explanation must be longer than 2, and shorter than 20 characters.")
    private String explanation;

    @NotNull
    @Min(value = 0)
    @Max(value = 4)
    private Integer accountType;
}
