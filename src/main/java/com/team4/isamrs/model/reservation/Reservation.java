package com.team4.isamrs.model.reservation;

import com.team4.isamrs.model.advertisement.SelectedOption;
import com.team4.isamrs.model.user.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Reservation {
    @Id
    @SequenceGenerator(name = "mySeqGenV3", sequenceName = "mySeqV3", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV3")
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany
    @JoinTable(
        name = "reservation_selected_option",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "selected_option_id"))
    private Set<SelectedOption> selectedOptions = new HashSet<>();

    @Column(name = "calculated_price", nullable = false)
    private BigDecimal calculatedPrice;

    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled;
}
