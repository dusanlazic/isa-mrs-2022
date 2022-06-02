package com.team4.isamrs.controller;

import com.team4.isamrs.dto.ResponseOK;
import com.team4.isamrs.dto.creation.AdminCreationDTO;
import com.team4.isamrs.dto.display.LoyaltyProgramCategoryDetailedDisplayDTO;
import com.team4.isamrs.dto.display.LoyaltyProgramSettingsDisplayDTO;
import com.team4.isamrs.dto.display.RegistrationRequestDisplayDTO;
import com.team4.isamrs.dto.display.RemovalRequestDisplayDTO;
import com.team4.isamrs.dto.updation.*;
import com.team4.isamrs.model.config.GlobalSetting;
import com.team4.isamrs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

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

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    private GlobalSettingsService globalSettingsService;

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

    @GetMapping(value = "/system/loyalty/settings", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public LoyaltyProgramSettingsDisplayDTO getLoyaltyProgramSettings() {
        return loyaltyProgramService.getSettings();
    }

    @PutMapping(value = "/system/loyalty/settings", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseOK updateLoyaltyProgramSettings(@Valid @RequestBody LoyaltyProgramSettingsUpdationDTO dto) {
        loyaltyProgramService.updateSettings(dto);
        return new ResponseOK("Settings saved.");
    }

    @GetMapping(value = "/system/loyalty/categories", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public List<LoyaltyProgramCategoryDetailedDisplayDTO> getLoyaltyProgramCategories(@RequestParam(required = false) String type) {
        return loyaltyProgramService.getCategories(type);
    }

    @PutMapping(value = "/system/loyalty/categories", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseOK updateLoyaltyProgramCategories(@Valid @RequestBody LoyaltyProgramCategoriesUpdationDTO dto) {
        loyaltyProgramService.updateCategories(dto);
        return new ResponseOK("Categories saved.");
    }

    @GetMapping(value = "/system/commission-rate", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public GlobalSetting getCommissionRate() {
        return globalSettingsService.getComissionRate();
    }

    @PutMapping(value = "/system/commission-rate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseOK updateCommissionRate(@Valid @RequestBody CommissionRateUpdationDTO dto) {
        globalSettingsService.updateCommissionRate(dto);
        return new ResponseOK("Commission rate updated.");
    }
}
