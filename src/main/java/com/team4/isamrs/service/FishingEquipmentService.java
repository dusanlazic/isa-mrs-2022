package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.FishingEquipmentCreationDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.model.advertisement.FishingEquipment;
import com.team4.isamrs.repository.FishingEquipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FishingEquipmentService {

    @Autowired
    private FishingEquipmentRepository fishingEquipmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> Collection<T> findAll(Class<T> returnType) {
        return fishingEquipmentRepository.findAll().stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        FishingEquipment fishingEquipment = fishingEquipmentRepository.findById(id).orElseThrow();

        return modelMapper.map(fishingEquipment, returnType);
    }

    public FishingEquipment create(FishingEquipmentCreationDTO dto) {
        FishingEquipment fishingEquipment = modelMapper.map(dto, FishingEquipment.class);

        fishingEquipmentRepository.save(fishingEquipment);
        return fishingEquipment;
    }
}
