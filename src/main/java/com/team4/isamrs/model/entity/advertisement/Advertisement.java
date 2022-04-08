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

    @Column(name = "title", nullable = false)
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "pricing_description", nullable = false)
    private String pricingDescription;

    @Column(name = "available_after", nullable = false)
    private LocalDateTime availableAfter;

    @Column(name = "available_until", nullable = false)
    private LocalDateTime availableUntil;

    @Column(name = "rules", nullable = false)
    private String rules;

    @Column(name = "currency", nullable = false)
    private String currency;

    /*
    e.g. WIFI, Pet friendly, TV
     */
    @ManyToMany(mappedBy = "advertisements", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Option> options = new HashSet<>();

    @ManyToMany(mappedBy = "subscriptions", fetch = FetchType.LAZY)
    private Set<Customer> subscribers = new HashSet<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<ServiceReview> reviews = new HashSet<>();
}