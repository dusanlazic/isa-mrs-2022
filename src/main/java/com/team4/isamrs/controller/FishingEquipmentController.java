package com.team4.isamrs.controller;

import com.team4.isamrs.dto.FishingEquipmentCreationDTO;
import com.team4.isamrs.dto.FishingEquipmentDisplayDTO;
import com.team4.isamrs.model.entity.adventure.FishingEquipment;
import com.team4.isamrs.service.FishingEquipmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fishing-equipment")
@CrossOrigin(origins = "*")
public class FishingEquipmentController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FishingEquipmentService fishingEquipmentService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FishingEquipmentDisplayDTO>> findAllFishingEquipment() {
        Collection<FishingEquipment> fishingEquipment = fishingEquipmentService.findAll();

        Collection<FishingEquipmentDisplayDTO> dto = fishingEquipment.stream()
                .map(e -> modelMapper.map(e, FishingEquipmentDisplayDTO.class))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FishingEquipmentDisplayDTO> findFishingEquipmentById(@PathVariable Long id) {
        Optional<FishingEquipment> fishingEquipment = fishingEquipmentService.findById(id);

        if (fishingEquipment.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        FishingEquipmentDisplayDTO dto = modelMapper.map(fishingEquipment.get(), FishingEquipmentDisplayDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.FOUND);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> addFishingEqupiment(@Valid @RequestBody FishingEquipmentCreationDTO dto) throws URISyntaxException {
        FishingEquipment fishingEquipment = modelMapper.map(dto, FishingEquipment.class);

        Long id = fishingEquipmentService.createFishingEquipment(fishingEquipment);
        if (id == null)
            return ResponseEntity.internalServerError().build();

        String uri = "/fishing-equipment/" + id;
        return ResponseEntity.created(new URI(uri)).body("New Fishing Equipment created at: " + uri);
    }
}
