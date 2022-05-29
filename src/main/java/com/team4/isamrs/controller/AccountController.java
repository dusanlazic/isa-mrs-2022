package com.team4.isamrs.controller;

import com.team4.isamrs.dto.ResponseOK;
import com.team4.isamrs.dto.creation.CustomerCreationDTO;
import com.team4.isamrs.dto.creation.RegistrationRequestCreationDTO;
import com.team4.isamrs.dto.creation.RemovalRequestCreationDTO;
import com.team4.isamrs.dto.display.AccountDisplayDTO;
import com.team4.isamrs.dto.updation.AccountUpdationDTO;
import com.team4.isamrs.dto.updation.PasswordUpdationDTO;
import com.team4.isamrs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/account",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/whoami")
    public AccountDisplayDTO whoAmI(Authentication auth) {
        return accountService.whoAmI(auth);
    }

    @GetMapping(value = "/{id}")
    public AccountDisplayDTO findById(@PathVariable Long id) {
        return accountService.findById(id, AccountDisplayDTO.class);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseOK updateAccount(@Valid @RequestBody AccountUpdationDTO dto, Authentication auth) {
        accountService.updateAccount(dto, auth);
        return new ResponseOK("Account updated.");
    }

    @PatchMapping("/password")
    public ResponseOK changePassword(@Valid @RequestBody PasswordUpdationDTO dto, Authentication auth) {
        accountService.changePassword(dto, auth);
        return new ResponseOK("Password updated.");
    }

    @DeleteMapping("")
    public ResponseOK delete(@Valid @RequestBody RemovalRequestCreationDTO removalRequest, Authentication auth) {
        accountService.createRemovalRequest(removalRequest, auth);
        return new ResponseOK("Request sent.");
    }

    @PostMapping("/register/advertiser")
    public ResponseOK registerAdvertiser(@Valid @RequestBody RegistrationRequestCreationDTO registrationRequest) {
        accountService.createRegistrationRequest(registrationRequest);
        return new ResponseOK("Registration request created.");
    }

    @PostMapping("/register/customer")
    public ResponseOK registerClient(@Valid @RequestBody CustomerCreationDTO customer) {
        accountService.createClient(customer);
        return new ResponseOK("Registration confirmation sent.");
    }

    @GetMapping("/register/confirm/{token}")
    public ResponseOK confirmRegistration(@PathVariable String token) {
        accountService.confirmRegistration(token);
        return new ResponseOK("Registration confirmed.");
    }
}
