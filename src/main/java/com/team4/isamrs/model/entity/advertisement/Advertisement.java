package com.team4.isamrs.model.entity.advertisement;

import com.team4.isamrs.model.entity.review.ServiceReview;
import com.team4.isamrs.model.entity.user.Advertiser;
import com.team4.isamrs.model.entity.user.Customer;

import javax.persistence.*;
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
    private LocalDateTime availableAfter;

    @Column
    private LocalDateTime availableUntil;

    @Column
    private String rules;

    @Column
    private String currency;

    /*
    e.g. WIFI, Pet friendly, TV
     */
    @ManyToMany(mappedBy = "advertisements", fetch = FetchType.LAZY)
    private Set<Tags> tags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Option> options = new HashSet<>();

    @ManyToMany(mappedBy = "subscriptions", fetch = FetchType.LAZY)
    private Set<Customer> subscribers = new HashSet<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<ServiceReview> reviews = new HashSet<>();
}
