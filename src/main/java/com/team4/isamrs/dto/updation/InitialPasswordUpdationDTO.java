package com.team4.isamrs.dto.updation;

import com.team4.isamrs.validation.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class InitialPasswordUpdationDTO {
    @NotBlank
    @Size(min = 8, message = "Password must be longer than 8 characters.")
    private String password;

    @NotBlank
    private String passwordConfirmation;
}
