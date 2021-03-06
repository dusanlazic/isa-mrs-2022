package com.team4.isamrs.controller;

import com.team4.isamrs.dto.ResponseCreated;
import com.team4.isamrs.dto.ResponseOK;
import com.team4.isamrs.dto.creation.AdventureAdCreationDTO;
import com.team4.isamrs.dto.display.AdventureAdDisplayDTO;
import com.team4.isamrs.dto.display.AdventureAdSimpleDisplayDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping(
        value = "/ads/adventures",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AdventureController {

    @Autowired
    private AdventureAdService adventureAdService;

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping(value = "")
    public Collection<AdventureAdDisplayDTO> findAll() {
        return adventureAdService.findAll(AdventureAdDisplayDTO.class);
    }

    @GetMapping(value = "/{id}")
    public AdventureAdDisplayDTO findById(@PathVariable Long id) {
        return adventureAdService.findById(id, AdventureAdDisplayDTO.class);
    }

    @GetMapping(value = "/top6")
    public Collection<AdventureAdSimpleDisplayDTO> findTopSix() {
        return adventureAdService.findTopSix();
    }

    @GetMapping(value = "/search")
    public Page<AdventureAdSimpleDisplayDTO> search(
            @RequestParam(required = false, defaultValue = "") String where,
            @RequestParam(required = false, defaultValue = "0") int guests,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam int page, @RequestParam String sorting, @RequestParam boolean descending) {
        return adventureAdService.search(where, guests, startDate, endDate,
                PageRequest.of(page, 20), sorting, descending);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseCreated create(@Valid @RequestBody AdventureAdCreationDTO dto, Authentication auth) {
        return new ResponseCreated("Adventure ad created.", adventureAdService.create(dto, auth).getId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseOK update(@PathVariable Long id, @Valid @RequestBody AdventureAdUpdationDTO dto, Authentication auth) {
        adventureAdService.update(id, dto, auth);
        return new ResponseOK("Adventure ad updated.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseOK delete(@PathVariable Long id, Authentication auth) {
        adventureAdService.delete(id, auth);
        return new ResponseOK("Adventure ad deleted.");
    }

    @PutMapping(value = "/{id}/availability-period", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseOK updateAvailabilityPeriod(@PathVariable Long id, @Valid @RequestBody AvailabilityPeriodUpdationDTO dto, Authentication auth) {
        advertisementService.updateAvailabilityPeriod(id, dto, auth);
        return new ResponseOK("Availability period updated.");
    }
}
