package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.AdventureAdCreationDTO;
import com.team4.isamrs.dto.creation.HourlyPriceCreationDTO;
import com.team4.isamrs.dto.display.AdventureAdDisplayDTO;
import com.team4.isamrs.dto.display.HourlyPriceDisplayDTO;
import com.team4.isamrs.dto.updation.AdventureAdUpdationDTO;
import com.team4.isamrs.service.AdventureAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(
        value = "/ads/adventures",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdventureController {

    @Autowired
    private AdventureAdService adventureAdService;

    @GetMapping(value = "")
    public ResponseEntity<Collection<AdventureAdDisplayDTO>> findAll() {
        return new ResponseEntity<>(adventureAdService.findAll(AdventureAdDisplayDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdventureAdDisplayDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(adventureAdService.findById(id, AdventureAdDisplayDTO.class), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> create(@Valid @RequestBody AdventureAdCreationDTO dto) {
        return ResponseEntity.created(URI.create("/ads/adventures/" + adventureAdService.create(dto).getId()))
                .body("Adventure ad created.");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody AdventureAdUpdationDTO dto) {
        adventureAdService.update(id, dto);
        return ResponseEntity.ok("Adventure ad updated.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        adventureAdService.delete(id);
        return ResponseEntity.ok("Adventure ad deleted.");
    }

    @GetMapping("/{id}/prices")
    public ResponseEntity<Collection<HourlyPriceDisplayDTO>> getPrices(@PathVariable Long id) {
        return new ResponseEntity<>(adventureAdService.getPrices(id, HourlyPriceDisplayDTO.class), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/prices", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> addPrice(@PathVariable Long id, @Valid @RequestBody HourlyPriceCreationDTO dto) {
        adventureAdService.addPrice(id, dto);
        return ResponseEntity.created(URI.create("/ads/adventures/" + id))
                             .body("Hourly price created.");
    }

    @PutMapping(value = "/{id}/prices/{priceId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> updatePrice(@PathVariable Long id, @PathVariable Long priceId, @Valid @RequestBody HourlyPriceCreationDTO dto) {
        adventureAdService.updatePrice(id, priceId, dto);
        return ResponseEntity.ok().body("Hourly price updated.");
    }

    @DeleteMapping("/{id}/prices/{priceId}")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> removePrice(@PathVariable Long id, @PathVariable Long priceId) {
        adventureAdService.removePrice(id, priceId);
        return ResponseEntity.ok().body("Hourly price deleted.");
    }
}
