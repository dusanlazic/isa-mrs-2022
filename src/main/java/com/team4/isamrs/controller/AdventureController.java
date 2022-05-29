package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.AdventureAdCreationDTO;
import com.team4.isamrs.dto.display.AdventureAdDisplayDTO;
import com.team4.isamrs.dto.display.AdventureAdSimpleDisplayDTO;
import com.team4.isamrs.dto.display.ResortAdSimpleDisplayDTO;
import com.team4.isamrs.dto.updation.AdventureAdUpdationDTO;
import com.team4.isamrs.dto.updation.AvailabilityPeriodUpdationDTO;
import com.team4.isamrs.service.AdventureAdService;
import com.team4.isamrs.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping(
        value = "/ads/adventures",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdventureController {

    @Autowired
    private AdventureAdService adventureAdService;

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping(value = "")
    public ResponseEntity<Collection<AdventureAdDisplayDTO>> findAll() {
        return new ResponseEntity<>(adventureAdService.findAll(AdventureAdDisplayDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdventureAdDisplayDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(adventureAdService.findById(id, AdventureAdDisplayDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/top6")
    public ResponseEntity<Collection<AdventureAdSimpleDisplayDTO>> findTopSix() {
        return new ResponseEntity<>(adventureAdService.findTopSix(), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public Page<AdventureAdSimpleDisplayDTO> search(
            @RequestParam(required = false, defaultValue = "") String where,
            @RequestParam(required = false, defaultValue = "0") int guests,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam int page) {
        return adventureAdService.search(
                where,
                guests,
                startDate,
                endDate,
                PageRequest.of(page, 20));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> create(@Valid @RequestBody AdventureAdCreationDTO dto, Authentication auth) {
        return ResponseEntity.created(URI.create("/ads/adventures/" + adventureAdService.create(dto, auth).getId()))
                .body("Adventure ad created.");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody AdventureAdUpdationDTO dto, Authentication auth) {
        adventureAdService.update(id, dto, auth);
        return ResponseEntity.ok("Adventure ad updated.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id, Authentication auth) {
        adventureAdService.delete(id, auth);
        return ResponseEntity.ok("Adventure ad deleted.");
    }

    @PutMapping(value = "/{id}/availability-period", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RESORT_OWNER')")
    public ResponseEntity<String> updateAvailabilityPeriod(@PathVariable Long id, @Valid @RequestBody AvailabilityPeriodUpdationDTO dto, Authentication auth) {
        advertisementService.updateAvailabilityPeriod(id, dto, auth);
        return ResponseEntity.ok().body("Availability period updated.");
    }
}
