package com.team4.isamrs.dto.display;

import lombok.Data;

@Data
public class CustomerDisplayDTO implements DisplayDTO {
    private Long id;
    private String avatarPath;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String address;
    private String city;
    private String countryCode;
    private String phoneNumber;
    private String passwordHash;
    private Boolean active;
}
