package com.team4.isamrs.model.advertisement;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class FishingEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "fishing_equipment_ads",
            joinColumns = @JoinColumn(name = "fishing_equipment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"))
    private Set<Advertisement> advertisements = new HashSet<>();

    public FishingEquipment() {
    }

    public FishingEquipment(String name) {
        this.name = name;
    }
}
