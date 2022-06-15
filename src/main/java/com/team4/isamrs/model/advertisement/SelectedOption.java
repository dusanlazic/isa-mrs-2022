package com.team4.isamrs.model.advertisement;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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
