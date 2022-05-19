package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.AdminCreationDTO;
import com.team4.isamrs.dto.updation.InitialPasswordUpdationDTO;
import com.team4.isamrs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/admin",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AccountService accountService;

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
}
