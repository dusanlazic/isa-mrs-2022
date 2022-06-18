package com.team4.isamrs.model.reservation;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.SelectedOption;
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
public class QuickReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id")
    private Advertisement advertisement;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
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

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "taken", nullable = false)
    private Boolean taken;
}
