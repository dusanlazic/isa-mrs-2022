package com.team4.isamrs.controller;

import com.team4.isamrs.dto.ResponseCreated;
import com.team4.isamrs.dto.ResponseOK;
import com.team4.isamrs.dto.creation.ResortAdCreationDTO;
import com.team4.isamrs.dto.display.ResortAdDisplayDTO;
import com.team4.isamrs.dto.display.ResortAdSimpleDisplayDTO;
import com.team4.isamrs.dto.updation.AvailabilityPeriodUpdationDTO;
import com.team4.isamrs.dto.updation.ResortAdUpdationDTO;
import com.team4.isamrs.service.AdvertisementService;
import com.team4.isamrs.service.ResortAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping(value = "/ads/resorts",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ResortController {

    @Autowired
    private ResortAdService resortAdService;

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping(value = "")
    public Collection<ResortAdDisplayDTO> findAll() {
       return resortAdService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResortAdDisplayDTO findById(@PathVariable Long id) {
       return resortAdService.findById(id);
    }

    @GetMapping(value = "/top6")
    public Collection<ResortAdSimpleDisplayDTO> findTopSix() {
       return resortAdService.findTopSix();
    }

    @GetMapping(value = "/search")
    public Page<ResortAdSimpleDisplayDTO> search(
            @RequestParam(required = false, defaultValue = "") String where,
            @RequestParam(required = false, defaultValue = "0") int guests,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam int page) {
        return resortAdService.search(
                where,
                guests,
                startDate,
                endDate,
                PageRequest.of(page, 20));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RESORT_OWNER')")
    public ResponseCreated create(@Valid @RequestBody ResortAdCreationDTO dto, Authentication auth) {
        return new ResponseCreated("Resort ad created.", resortAdService.create(dto, auth).getId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RESORT_OWNER')")
    public ResponseOK update(@PathVariable Long id, @Valid @RequestBody ResortAdUpdationDTO dto, Authentication auth) {
        resortAdService.update(id, dto, auth);
        return new ResponseOK("Resort ad updated.");
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('RESORT_OWNER') or hasRole('ADMIN')")
    public ResponseOK delete(@PathVariable Long id, Authentication auth) {
        resortAdService.delete(id, auth);
        return new ResponseOK("Resort deleted.");
    }

    @PutMapping(value = "/{id}/availability-period", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('RESORT_OWNER')")
    public ResponseOK updateAvailabilityPeriod(@PathVariable Long id, @Valid @RequestBody AvailabilityPeriodUpdationDTO dto, Authentication auth) {
        advertisementService.updateAvailabilityPeriod(id, dto, auth);
        return new ResponseOK("Availability period updated.");
    }
}
