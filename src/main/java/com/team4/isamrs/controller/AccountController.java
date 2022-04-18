package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.AccountCreationDTO;
import com.team4.isamrs.dto.creation.OptionCreationDTO;
import com.team4.isamrs.dto.display.AccountDisplayDTO;
import com.team4.isamrs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> updateAccount(@Valid @RequestBody AccountCreationDTO dto) {
        accountService.updateAccount(dto);
        return ResponseEntity.ok().body("Account updated.");
    }
}
