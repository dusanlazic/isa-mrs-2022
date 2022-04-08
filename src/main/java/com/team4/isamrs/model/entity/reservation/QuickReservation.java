package com.team4.isamrs.model.entity.reservation;

import com.team4.isamrs.model.entity.advertisement.SelectedOption;

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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "quick_reservation_selected_option",
        joinColumns = @JoinColumn(name = "quick_reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "selected_option_id"))
    private Set<SelectedOption> selectedOptions = new HashSet<>();

    @Column(name = "valid_after", nullable = false)
    private LocalDateTime validAfter;

    @Column(name = "valid_until", nullable = false)
    private LocalDateTime validUntil;

    @Column(name = "calculated_old_price", nullable = false)
    private BigDecimal calculatedOldPrice;

    @Column(name = "new_price", nullable = false)
    private BigDecimal newPrice;
}
