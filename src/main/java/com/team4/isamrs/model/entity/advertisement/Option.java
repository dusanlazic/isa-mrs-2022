package com.team4.isamrs.model.entity.advertisement;

import javax.persistence.*;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer maxCount;
}
