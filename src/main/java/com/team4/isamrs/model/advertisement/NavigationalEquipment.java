package com.team4.isamrs.model.advertisement;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class NavigationalEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "navigational_equipment_ads",
            joinColumns = @JoinColumn(name = "navigational_equipment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"))
    private Set<Advertisement> advertisements = new HashSet<>();

    public NavigationalEquipment() {
    }

    public NavigationalEquipment(String name) {
        this.name = name;
    }
}
