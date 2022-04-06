package com.team4.isamrs.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Advertisement {
    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "advertiser_id")
    private Advertiser advertiser;

    @Column
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column
    private String description;

    @Column
    private String pricingDescription;

    @Column
    private BigDecimal basePrice;

    @Column
    private String currency;

    @Column
    private LocalDateTime availableAfter;

    @Column
    private LocalDateTime availableUntil;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Photo> photos = new HashSet<Photo>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Option> options = new HashSet<Option>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Action> actions = new HashSet<Action>();

    @Column
    private String rules;

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<Reservation>();

    @ManyToMany(mappedBy = "followedAdvertisements")
    private Set<Customer> followers = new HashSet<Customer>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<ServiceReview> reviews = new HashSet<ServiceReview>();
}
