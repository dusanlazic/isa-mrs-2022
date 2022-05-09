package com.team4.isamrs.repository;

import com.team4.isamrs.model.adventure.FishingEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FishingEquipmentRepository extends JpaRepository<FishingEquipment, Long> {
    Optional<FishingEquipment> findByNameIgnoreCase(String name);
}
