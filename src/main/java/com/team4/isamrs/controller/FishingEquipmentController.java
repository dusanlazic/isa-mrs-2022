package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.FishingEquipmentCreationDTO;
import com.team4.isamrs.dto.display.FishingEquipmentDisplayDTO;
import com.team4.isamrs.service.FishingEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(
        value = "/fishing-equipment",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class FishingEquipmentController {

    @Autowired
    private FishingEquipmentService fishingEquipmentService;

    @GetMapping("")
    public ResponseEntity<Collection<FishingEquipmentDisplayDTO>> findAll() {
        return new ResponseEntity<>(fishingEquipmentService.findAll(FishingEquipmentDisplayDTO.class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FishingEquipmentDisplayDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(fishingEquipmentService.findById(id, FishingEquipmentDisplayDTO.class), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> create(@Valid @RequestBody FishingEquipmentCreationDTO dto) {
        return ResponseEntity.created(URI.create("/fishing-equipment/" + fishingEquipmentService.create(dto).getId()))
                             .body("Fishing equipment created.");
    }
}
