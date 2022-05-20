package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.AdminCreationDTO;
import com.team4.isamrs.dto.display.RegistrationRequestDisplayDTO;
import com.team4.isamrs.dto.display.RemovalRequestDisplayDTO;
import com.team4.isamrs.dto.updation.InitialPasswordUpdationDTO;
import com.team4.isamrs.dto.updation.RegistrationRequestUpdationDTO;
import com.team4.isamrs.dto.updation.RemovalRequestUpdationDTO;
import com.team4.isamrs.service.AccountService;
import com.team4.isamrs.service.RegistrationRequestService;
import com.team4.isamrs.service.RemovalRequestService;
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
@RequestMapping(value = "/admin",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RegistrationRequestService registrationRequestService;

    @Autowired
    private RemovalRequestService removalRequestService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('SUPERUSER')")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody AdminCreationDTO dto) {
        accountService.createAdmin(dto);
        return ResponseEntity.ok().body("Administrator registered.");
    }

    @PatchMapping("/password")
    @PreAuthorize("hasRole('FRESH_ADMIN')")
    public ResponseEntity<String> changeInitialPassword(@Valid @RequestBody InitialPasswordUpdationDTO dto, Authentication auth) {
        accountService.changeInitialPassword(dto, auth);
        return ResponseEntity.ok().body("Password updated.");
    }

    @GetMapping(value = "/registration-requests", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<RegistrationRequestDisplayDTO>> findAllPendingRegistrationRequests() {
        return new ResponseEntity<>(registrationRequestService.findAllPending(RegistrationRequestDisplayDTO.class), HttpStatus.OK);
    }

    @PatchMapping(value = "/registration-requests/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> respondToRegistrationRequest(@PathVariable Long id, @Valid @RequestBody RegistrationRequestUpdationDTO dto) {
        registrationRequestService.respondToRequest(id, dto);
        return ResponseEntity.ok("Request resolved.");
    }

    @GetMapping(value = "/removal-requests", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<RemovalRequestDisplayDTO>> findAllPendingRemovalRequests() {
        return new ResponseEntity<>(removalRequestService.findAllPending(RemovalRequestDisplayDTO.class), HttpStatus.OK);
    }

    @PatchMapping(value = "/removal-requests/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> respondToRemovalRequest(@PathVariable Long id, @Valid @RequestBody RemovalRequestUpdationDTO dto) {
        removalRequestService.respondToRequest(id, dto);
        return ResponseEntity.ok("Request resolved.");
    }
}
