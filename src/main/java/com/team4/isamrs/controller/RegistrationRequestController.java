package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.RegistrationRequestDisplayDTO;
import com.team4.isamrs.dto.updation.RegistrationRequestUpdationDTO;
import com.team4.isamrs.service.AccountService;
import com.team4.isamrs.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/admin/registration-requests")
@CrossOrigin(origins = "*")
public class RegistrationRequestController {

    @Autowired
    private RegistrationRequestService registrationRequestService;

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<RegistrationRequestDisplayDTO>> findAllPending() {
        return new ResponseEntity<>(registrationRequestService.findAllPending(RegistrationRequestDisplayDTO.class), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> respondToRequest(@PathVariable Long id, @Valid @RequestBody RegistrationRequestUpdationDTO dto) {
        registrationRequestService.respondToRequest(id, dto);
        return ResponseEntity.ok("Request resolved.");
    }
}
