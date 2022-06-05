package com.team4.isamrs.model.complaint;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.enumeration.ResponseStatus;
import com.team4.isamrs.model.user.Customer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "response_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ResponseStatus responseStatus;
}
