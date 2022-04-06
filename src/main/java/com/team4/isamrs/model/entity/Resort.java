package com.team4.isamrs.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Resort extends Advertisement {
    /*
    Number of beds for each room separated by commas.
    e.g. 3,1,0,1 => 4 rooms, 5 beds total: three in the first, one in the second, etc.
     */
    @Column
    private String numberOfBeds;
}
