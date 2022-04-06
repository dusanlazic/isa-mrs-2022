package com.team4.isamrs.model.entity;

import javax.persistence.*;

@Entity
public class LoyaltyProgramConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer pointsEarnedPerReservationForCustomers;

    @Column
    private Integer pointsEarnedPerReservationForAdvertisers;
}
