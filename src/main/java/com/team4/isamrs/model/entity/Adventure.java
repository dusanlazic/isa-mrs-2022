package com.team4.isamrs.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Adventure extends Advertisement {
    @Column
    private String instructorBio;

    @Column
    private Integer capacity;

    @Column
    private String fishingEquipment;

    @Column
    private BigDecimal cancellationFee;
}
