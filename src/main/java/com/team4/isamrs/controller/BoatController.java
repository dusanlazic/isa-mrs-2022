package com.team4.isamrs.controller;

import com.team4.isamrs.dto.ResponseCreated;
import com.team4.isamrs.dto.ResponseOK;
import com.team4.isamrs.dto.creation.BoatAdCreationDTO;
import com.team4.isamrs.dto.display.BoatAdDisplayDTO;
import com.team4.isamrs.dto.display.BoatAdSimpleDisplayDTO;
import com.team4.isamrs.dto.updation.AvailabilityPeriodUpdationDTO;
import com.team4.isamrs.dto.updation.BoatAdUpdationDTO;
import com.team4.isamrs.service.AdvertisementService;
import com.team4.isamrs.service.BoatAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(value = "/ads/boats",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class BoatController {

    @Autowired
    private BoatAdService boatAdService;

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping(value = "")
    public Collection<BoatAdDisplayDTO> findAll() {
        return boatAdService.findAll(BoatAdDisplayDTO.class);
    }

    @GetMapping(value = "/{id}")
    public BoatAdDisplayDTO findById(@PathVariable Long id) {
        return boatAdService.findById(id, BoatAdDisplayDTO.class);
    }

    @GetMapping(value = "/top6")
    public Collection<BoatAdSimpleDisplayDTO> findTopSix() {
        return boatAdService.findTopSix();
    }

    @GetMapping(value = "/search")
    public Page<BoatAdSimpleDisplayDTO> search(@RequestParam int page) {
        return boatAdService.search(page);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('BOAT_OWNER')")
    public ResponseCreated create(@Valid @RequestBody BoatAdCreationDTO dto, Authentication auth) {
        return new ResponseCreated("Boat ad created.", boatAdService.create(dto, auth).getId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('BOAT_OWNER')")
    public ResponseOK update(@PathVariable Long id, @Valid @RequestBody BoatAdUpdationDTO dto, Authentication auth) {
        boatAdService.update(id, dto, auth);
        return new ResponseOK("Boat ad updated.");
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('BOAT_OWNER') or hasRole('ADMIN')")
    public ResponseOK delete(@PathVariable Long id, Authentication auth) {
        boatAdService.delete(id, auth);
        return new ResponseOK("Boat deleted.");
    }

    @PutMapping(value = "/{id}/availability-period", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('BOAT_OWNER')")
    public ResponseOK updateAvailabilityPeriod(@PathVariable Long id, @Valid @RequestBody AvailabilityPeriodUpdationDTO dto, Authentication auth) {
        advertisementService.updateAvailabilityPeriod(id, dto, auth);
        return new ResponseOK("Availability period updated.");
    }
}
