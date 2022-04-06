package com.team4.isamrs.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    private LocalDateTime reservationStartTime;

    @Column
    private LocalDateTime reservationEndTime;

    @ManyToMany
    @JoinTable(
        name = "reservation_option",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> selectedOptions = new HashSet<Option>();

    @Column
    private BigDecimal calculatedPrice;

    @Column
    private Boolean cancelled;
}
