package com.team4.isamrs.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @ManyToMany
    @JoinTable(
        name = "action_option",
        joinColumns = @JoinColumn(name = "action_id"),
        inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> selectedOptions = new HashSet<Option>();

    @Column
    private LocalDateTime reservationStartTime;

    @Column
    private LocalDateTime reservationEndTime;

    @Column
    private Integer capacity;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column
    private String additionalServices;

    @Column
    private BigDecimal originalPrice;

    @Column
    private BigDecimal newPrice;

    @Column
    private String currency;
}
