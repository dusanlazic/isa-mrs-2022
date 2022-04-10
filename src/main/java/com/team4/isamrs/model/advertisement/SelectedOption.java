package com.team4.isamrs.model.advertisement;

import javax.persistence.*;

@Entity
public class SelectedOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    @Column(name = "count", nullable = false)
    private Integer count;
}
