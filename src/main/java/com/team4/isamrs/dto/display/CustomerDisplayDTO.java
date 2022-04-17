package com.team4.isamrs.dto.display;

import lombok.Data;

@Data
public class CustomerDisplayDTO implements DisplayDTO {
    private Long id;
    private PhotoDisplayDTO avatar;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String city;
    private String countryCode;
    private String phoneNumber;
    private Boolean active;
}
