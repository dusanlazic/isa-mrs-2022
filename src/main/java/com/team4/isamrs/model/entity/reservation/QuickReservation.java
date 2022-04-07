package com.team4.isamrs.model.entity.reservation;

import com.team4.isamrs.model.entity.advertisement.Option;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class QuickReservation {
    @Id
    @SequenceGenerator(name = "mySeqGenV4", sequenceName = "mySeqV4", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV4")
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "reservation_option",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> selectedOptions = new HashSet<Option>();

    @Column
    private LocalDateTime validAfter;

    @Column
    private LocalDateTime validUntil;

    @Column
    private BigDecimal calculatedOldPrice;

    @Column
    private BigDecimal newPrice;
}
