package com.team4.isamrs.model.entity.loyalty;

import javax.persistence.*;

@Entity
public class LoyaltyProgramConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer customersPointsPerReservation;

    @Column
    private Integer advertisersPointsPerReservation;
}
