package com.team4.isamrs.model.entity.adventure;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FishingEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "fishing_equipment_ads",
            joinColumns = @JoinColumn(name = "fishing_equipment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"))
    private Set<AdventureAd> advertisements = new HashSet<>();
}
