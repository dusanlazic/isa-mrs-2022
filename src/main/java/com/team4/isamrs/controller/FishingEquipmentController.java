package com.team4.isamrs.controller;

import com.team4.isamrs.dto.ResponseCreated;
import com.team4.isamrs.dto.creation.FishingEquipmentCreationDTO;
import com.team4.isamrs.dto.display.FishingEquipmentDisplayDTO;
import com.team4.isamrs.service.FishingEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(
        value = "/fishing-equipment",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class FishingEquipmentController {

    @Autowired
    private FishingEquipmentService fishingEquipmentService;

    @GetMapping("")
    public Collection<FishingEquipmentDisplayDTO> findAll() {
        return fishingEquipmentService.findAll(FishingEquipmentDisplayDTO.class);
    }

    @GetMapping("/{id}")
    public FishingEquipmentDisplayDTO findById(@PathVariable Long id) {
        return fishingEquipmentService.findById(id, FishingEquipmentDisplayDTO.class);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseCreated create(@Valid @RequestBody FishingEquipmentCreationDTO dto) {
        return new ResponseCreated("Fishing equipment created.", fishingEquipmentService.create(dto).getId());
    }
}
