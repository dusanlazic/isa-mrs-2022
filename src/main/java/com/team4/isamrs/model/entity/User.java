package com.team4.isamrs.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class User {
    /*
    Will be replaced by Spring Security's User class
     */
    @Id
    @SequenceGenerator(name = "mySeqGenV1", sequenceName = "mySeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String emailAddress;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String countryCode;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column
    private String passwordHash;

    @Column
    private Boolean active;
}
