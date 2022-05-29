package com.team4.isamrs.controller;

import com.team4.isamrs.dto.ResponseOK;
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
import org.springframework.http.MediaType;
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
    public ResponseOK registerAdmin(@Valid @RequestBody AdminCreationDTO dto) {
        accountService.createAdmin(dto);
        return new ResponseOK("Administrator registered.");
    }

    @PatchMapping("/password")
    @PreAuthorize("hasRole('FRESH_ADMIN')")
    public ResponseOK changeInitialPassword(@Valid @RequestBody InitialPasswordUpdationDTO dto, Authentication auth) {
        accountService.changeInitialPassword(dto, auth);
        return new ResponseOK("Password updated.");
    }

    @GetMapping(value = "/registration-requests", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<RegistrationRequestDisplayDTO> findAllPendingRegistrationRequests() {
        return registrationRequestService.findAllPending(RegistrationRequestDisplayDTO.class);
    }

    @PatchMapping(value = "/registration-requests/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseOK respondToRegistrationRequest(@PathVariable Long id, @Valid @RequestBody RegistrationRequestUpdationDTO dto) {
        registrationRequestService.respondToRequest(id, dto);
        return new ResponseOK("Request resolved.");
    }

    @GetMapping(value = "/removal-requests", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<RemovalRequestDisplayDTO> findAllPendingRemovalRequests() {
        return removalRequestService.findAllPending(RemovalRequestDisplayDTO.class);
    }

    @PatchMapping(value = "/removal-requests/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseOK respondToRemovalRequest(@PathVariable Long id, @Valid @RequestBody RemovalRequestUpdationDTO dto) {
        removalRequestService.respondToRequest(id, dto);
        return new ResponseOK("Request resolved.");
    }
}
