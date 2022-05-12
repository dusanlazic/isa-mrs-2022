package com.team4.isamrs.repository;

import com.team4.isamrs.model.boat.NavigationalEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NavigationalEquipmentRepository extends JpaRepository<NavigationalEquipment, Long> {
    Optional<NavigationalEquipment> findByNameIgnoreCase(String name);
}
