package com.team4.isamrs.model.entity;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String countryCode;

    @Column
    private String postalCode;

    @Column
    private String latitude;

    @Column
    private String longitude;
}
