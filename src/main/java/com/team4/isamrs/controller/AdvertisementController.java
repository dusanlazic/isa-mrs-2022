package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.OptionCreationDTO;
import com.team4.isamrs.dto.display.OptionDisplayDTO;
import com.team4.isamrs.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(
        value = "/ads",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/{id}/options")
    public ResponseEntity<Collection<OptionDisplayDTO>> getOptions(@PathVariable Long id) {
        return new ResponseEntity<>(advertisementService.getOptions(id, OptionDisplayDTO.class), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/options", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addOption(@PathVariable Long id, @Valid @RequestBody OptionCreationDTO dto) {
        advertisementService.addOption(id, dto);
        return ResponseEntity.ok().body("Option created.");
    }

    @PutMapping(value = "/{id}/options/{optionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateOption(@PathVariable Long id, @PathVariable Long optionId, @Valid @RequestBody OptionCreationDTO dto) {
        advertisementService.updateOption(id, optionId, dto);
        return ResponseEntity.ok().body("Option updated.");
    }

    @DeleteMapping("/{id}/options/{optionId}")
    public ResponseEntity<String> removeOption(@PathVariable Long id, @PathVariable Long optionId) {
        advertisementService.removeOption(id, optionId);
        return ResponseEntity.ok().body("Option deleted.");
    }
}
