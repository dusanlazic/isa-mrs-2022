package com.team4.isamrs.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.team4.isamrs.dto.creation.CustomerCreationDTO;
import com.team4.isamrs.dto.creation.RegistrationRequestCreationDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.updation.AccountUpdationDTO;
import com.team4.isamrs.exception.ConfirmationLinkExpiredException;
import com.team4.isamrs.exception.EmailAlreadyExistsException;
import com.team4.isamrs.exception.PhoneNumberAlreadyExistsException;
import com.team4.isamrs.model.user.*;
import com.team4.isamrs.repository.*;
import com.team4.isamrs.security.EmailSender;
import com.team4.isamrs.security.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private TokenUtils tokenUtils;

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(), returnType);
    }

    public void updateAccount(AccountUpdationDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();

        modelMapper.map(dto, user);
        userRepository.save(user);
    }

    public void createTestAccount() {

        Advertiser advertiser = new Advertiser();
        advertiser.setEnabled(true);
        advertiser.setAddress("Kod mene kući BB");
        advertiser.setCity("Novi Sad");
        advertiser.setCountryCode("RS");
        advertiser.setUsername("contact@dusanlazic.com");
        advertiser.setFirstName("Dusan");
        advertiser.setLastName("Lazic");
        advertiser.setPassword(passwordEncoder.encode("13371337"));
        advertiser.getAuthorities().add(roleRepository.findByName("ROLE_FISHING_INSTRUCTOR").get());
        advertiser.setAvatar(photoRepository.getById(UUID.fromString("ac29818c-5e95-438c-85ff-da0a25cd188c")));
        advertiser.setPhoneNumber("065-1337");
        userRepository.save(advertiser);

        User user = userRepository.findById(2L).orElseThrow();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getAuthorities().add(roleRepository.findByName("ROLE_BOAT_OWNER").get());
        user.getAuthorities().add(roleRepository.findByName("ROLE_ADVERTISER").get());
        userRepository.save(user);
    }

    public void initializeRoles() {
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_CUSTOMER"));
        roleRepository.save(new Role("ROLE_ADVERTISER"));
        roleRepository.save(new Role("ROLE_FISHING_INSTRUCTOR"));
        roleRepository.save(new Role("ROLE_RESORT_OWNER"));
        roleRepository.save(new Role("ROLE_BOAT_OWNER"));
    }

    public void createRegistrationRequest(RegistrationRequestCreationDTO registrationRequestDTO) {

        checkForExistingEmail(registrationRequestDTO.getUsername());
        checkForExistingPhoneNumber(registrationRequestDTO.getPhoneNumber());

        RegistrationRequest registrationRequest = modelMapper.map(registrationRequestDTO, RegistrationRequest.class);
        registrationRequest.setCreatedAt(LocalDateTime.now());
        registrationRequest.setPasswordHash(passwordEncoder.encode(registrationRequestDTO.getPassword()));
        registrationRequestRepository.save(registrationRequest);
    }

    public void createClient(CustomerCreationDTO customerDTO) {
        checkForExistingEmail(customerDTO.getUsername());
        checkForExistingPhoneNumber(customerDTO.getPhoneNumber());

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customer.setEnabled(false);
        customer.getAuthorities().add(roleRepository.findByName("ROLE_CUSTOMER").orElseThrow());
        customerRepository.save(customer);

        String token = tokenUtils.generateConfirmationToken(customer);
        emailSender.sendRegistrationEmail(customer, token);
    }

    private void checkForExistingEmail(String username) {
        if (userRepository.findByUsername(username).isPresent() ||
            registrationRequestRepository.findByUsername(username).isPresent())
            throw new EmailAlreadyExistsException("Email already exists.");
    }

    private void checkForExistingPhoneNumber(String phoneNumber) {
        if (userRepository.findByPhoneNumber(phoneNumber).isPresent() ||
            registrationRequestRepository.findByPhoneNumber(phoneNumber).isPresent())
            throw new PhoneNumberAlreadyExistsException("Phone number already exists.");
    }

    public void confirmRegistration(String confirmationToken) {
        try {
            DecodedJWT decodedJWT = tokenUtils.verifyToken(confirmationToken);
            User user = userRepository.findByUsername(decodedJWT.getSubject()).orElseThrow();
            user.setEnabled(true);
            userRepository.save(user);

        } catch (TokenExpiredException e) {
            throw new ConfirmationLinkExpiredException("Confirmation link expired.");
        }
    }
}
