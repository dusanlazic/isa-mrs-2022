package com.team4.isamrs.controller;

import com.team4.isamrs.dto.ResponseOK;
import com.team4.isamrs.dto.creation.OptionCreationDTO;
import com.team4.isamrs.dto.display.OptionDisplayDTO;
import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.dto.updation.AvailabilityPeriodUpdationDTO;
import com.team4.isamrs.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADVERTISER')")
    public ResponseOK delete(@PathVariable Long id, Authentication auth) {
        advertisementService.delete(id, auth);
        return new ResponseOK("Advertisement deleted.");
    }

    @GetMapping("/{id}/options")
    public ResponseEntity<Collection<OptionDisplayDTO>> getOptions(@PathVariable Long id) {
        return new ResponseEntity<>(advertisementService.getOptions(id, OptionDisplayDTO.class), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/options", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADVERTISER')")
    public ResponseOK addOption(@PathVariable Long id, @Valid @RequestBody OptionCreationDTO dto, Authentication auth) {
        advertisementService.addOption(id, dto, auth);
        return new ResponseOK("Option created.");
    }

    @PutMapping(value = "/{id}/options/{optionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADVERTISER')")
    public ResponseOK updateOption(@PathVariable Long id,
                                               @PathVariable Long optionId,
                                               @Valid @RequestBody OptionCreationDTO dto,
                                               Authentication auth) {
        advertisementService.updateOption(id, optionId, dto, auth);
        return new ResponseOK("Option updated.");
    }

    @DeleteMapping("/{id}/options/{optionId}")
    @PreAuthorize("hasRole('ADVERTISER')")
    public ResponseOK removeOption(@PathVariable Long id, @PathVariable Long optionId, Authentication auth) {
        advertisementService.removeOption(id, optionId, auth);
        return new ResponseOK("Option deleted.");
    }

    @GetMapping(value = "/{id}/rating")
    public Double findRating(@PathVariable Long id) {
        return advertisementService.findRating(id);
    }

    @GetMapping("/{id}/reviews")
    public Collection<ServiceReviewDisplayDTO> getReviews(@PathVariable Long id) {
        return advertisementService.getReviews(id);
    }

    @PutMapping(value = "/{id}/availability-period", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADVERTISER')")
    public ResponseOK updateAvailabilityPeriod(@PathVariable Long id, @Valid @RequestBody AvailabilityPeriodUpdationDTO dto, Authentication auth) {
        advertisementService.updateAvailabilityPeriod(id, dto, auth);
        return new ResponseOK("Availability period updated.");
    }
}
