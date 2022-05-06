package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.CustomerCreationDTO;
import com.team4.isamrs.dto.creation.RegistrationRequestCreationDTO;
import com.team4.isamrs.dto.display.AccountDisplayDTO;
import com.team4.isamrs.dto.updation.AccountUpdationDTO;
import com.team4.isamrs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDisplayDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.findById(id, AccountDisplayDTO.class), HttpStatus.OK);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAccount(@Valid @RequestBody AccountUpdationDTO dto, Authentication auth) {
        accountService.updateAccount(dto, auth);
        return ResponseEntity.ok().body("Account updated.");
    }

    @PostMapping("/advertiser-registration")
    public ResponseEntity<String> registerAdvertiser(@Valid @RequestBody RegistrationRequestCreationDTO registrationRequest) {
        accountService.createRegistrationRequest(registrationRequest);
        return ResponseEntity.ok().body("Registration request created.");
    }

    @PostMapping("customer-registration")
    public ResponseEntity<String> registerClient(@Valid @RequestBody CustomerCreationDTO customer) {
        accountService.createClient(customer);
        return ResponseEntity.ok().body("Registration confirmation sent.");
    }

    @GetMapping("/confirm-registration/{token}")
    public ResponseEntity<String> confirmRegistration(@PathVariable String token) {
        accountService.confirmRegistration(token);
        return ResponseEntity.ok().body("Registration confirmed.");
    }
}
