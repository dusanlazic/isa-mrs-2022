package com.team4.isamrs.security;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @Email
    private String username;

    @NotBlank
    private String password;
}
