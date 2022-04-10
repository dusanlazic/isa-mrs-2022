package com.team4.isamrs.service;

import com.team4.isamrs.model.entity.adventure.FishingEquipment;
import com.team4.isamrs.repository.FishingEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class FishingEquipmentService {

    @Autowired
    private FishingEquipmentRepository fishingEquipmentRepository;

    public Collection<FishingEquipment> findAll() {
        return fishingEquipmentRepository.findAll();
    }

    public Optional<FishingEquipment> findById(Long id) {
        return fishingEquipmentRepository.findById(id);
    }

    public Long createFishingEquipment(FishingEquipment fishingEquipment) {
        try {
            fishingEquipmentRepository.save(fishingEquipment);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return fishingEquipment.getId();
    }
}
