package com.team4.isamrs.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Boat extends Advertisement {
    /*
    Number of beds for each room separated by commas.
    i.e. 3,1,0,1 => 4 rooms, 5 beds total: three in the first, one in the second, etc.
     */
    @Column
    private String boatType;

    @Column
    private String boatLength;

    @Column
    private String engineNumber;

    @Column
    private String enginePower;

    @Column
    private String boatSpeed;

    @Column
    private Boolean hasGPS;

    @Column
    private Boolean hasRadar;

    @Column
    private Boolean hasVHFRadio;

    @Column
    private Boolean hasFishfinder;

    @Column
    private String fishingEquipment;

    @Column
    private Integer capacity;

    @Column
    private BigDecimal cancellationFee;
}
