package com.team4.isamrs.dto.display;

import lombok.Data;

@Data
public class AccountDisplayDTO implements DisplayDTO {
    private Long id;
    private PhotoBriefDisplayDTO avatar;
    private String firstName;
    private String lastName;
    private String username;
    private String address;
    private String city;
    private String countryCode;
    private String phoneNumber;
    private String accountType;
    private Boolean active;
}