package com.team4.isamrs.model.loyalty;

import javax.persistence.*;

@Entity
public class LoyaltyProgramConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customers_points_per_reservation", nullable = false)
    private Integer customersPointsPerReservation;

    @Column(name = "advertisers_points_per_reservation", nullable = false)
    private Integer advertisersPointsPerReservation;
}
