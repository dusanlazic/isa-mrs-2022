package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.BoatAdCreationDTO;
import com.team4.isamrs.dto.display.BoatAdDisplayDTO;
import com.team4.isamrs.dto.display.BoatAdSimpleDisplayDTO;
import com.team4.isamrs.dto.updation.BoatAdUpdationDTO;
import com.team4.isamrs.service.BoatAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(value = "/ads/boats",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class BoatController {

    @Autowired
    private BoatAdService boatAdService;

    @GetMapping(value = "")
    public ResponseEntity<Collection<BoatAdDisplayDTO>> findAll() {
        return new ResponseEntity<>(boatAdService.findAll(BoatAdDisplayDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/top6")
    public ResponseEntity<Collection<BoatAdSimpleDisplayDTO>> findTopSix() {
        return new ResponseEntity<>(boatAdService.findTopSix(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BoatAdDisplayDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(boatAdService.findById(id, BoatAdDisplayDTO.class), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('BOAT_OWNER')")
    public ResponseEntity<String> create(@Valid @RequestBody BoatAdCreationDTO dto, Authentication auth) {
        return ResponseEntity.created(URI.create("/ads/boats/" + boatAdService.create(dto, auth).getId()))
                .body("Boat ad created.");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('BOAT_OWNER')")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody BoatAdUpdationDTO dto, Authentication auth) {
        boatAdService.update(id, dto, auth);
        return ResponseEntity.ok("Boat ad updated.");
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('BOAT_OWNER') or hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id, Authentication auth) {
        boatAdService.delete(id, auth);
        return ResponseEntity.ok("Boat deleted.");
    }
}
