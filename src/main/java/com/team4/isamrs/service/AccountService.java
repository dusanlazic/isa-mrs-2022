package com.team4.isamrs.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.team4.isamrs.dto.creation.AdminCreationDTO;
import com.team4.isamrs.dto.creation.CustomerCreationDTO;
import com.team4.isamrs.dto.creation.RegistrationRequestCreationDTO;
import com.team4.isamrs.dto.creation.RemovalRequestCreationDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.display.SessionDisplayDTO;
import com.team4.isamrs.dto.updation.AccountUpdationDTO;
import com.team4.isamrs.dto.updation.InitialPasswordUpdationDTO;
import com.team4.isamrs.dto.updation.PasswordUpdationDTO;
import com.team4.isamrs.exception.*;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
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
    private RemovalRequestRepository removalRequestRepository;

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

    public SessionDisplayDTO whoAmI(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return modelMapper.map(user, SessionDisplayDTO.class);
    }

    public <T extends DisplayDTO> T getAccount(Authentication auth, Class<T> returnType) {
        User user = (User) auth.getPrincipal();
        return modelMapper.map(user, returnType);
    }

    public void updateAccount(AccountUpdationDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();

        modelMapper.map(dto, user);
        userRepository.save(user);
    }

    public void changeInitialPassword(InitialPasswordUpdationDTO dto, Authentication auth) {
        Administrator admin = (Administrator) auth.getPrincipal();

        if (passwordEncoder.matches(dto.getPassword(), admin.getPassword()))
            throw new PasswordSameAsOldException();

        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.getAuthorities().clear();
        admin.getAuthorities().add(roleRepository.findByName("ROLE_ADMIN").orElseThrow());
        userRepository.save(admin);
    }

    public void changePassword(PasswordUpdationDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword()))
            throw new IncorrectCurrentPasswordException();
        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword()))
            throw new PasswordSameAsOldException();

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    public void initializeRoles() {
        roleRepository.save(new Role("ROLE_SUPERUSER"));
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_FRESH_ADMIN"));
        roleRepository.save(new Role("ROLE_CUSTOMER"));
        roleRepository.save(new Role("ROLE_ADVERTISER"));
        roleRepository.save(new Role("ROLE_FISHING_INSTRUCTOR"));
        roleRepository.save(new Role("ROLE_RESORT_OWNER"));
        roleRepository.save(new Role("ROLE_BOAT_OWNER"));
    }

    public void createRemovalRequest(RemovalRequestCreationDTO removalRequestCreationDTO, Authentication auth) {
        User user = (User) auth.getPrincipal();
        checkForExistingRemovalRequest(user.getId());
        RemovalRequest removalRequest = new RemovalRequest();
        removalRequest.setExplanation(removalRequestCreationDTO.getExplanation());
        removalRequest.setUser(user);
        removalRequest.setCreatedAt(LocalDateTime.now());
        removalRequest.setApprovalStatus(ApprovalStatus.PENDING);
        removalRequestRepository.save(removalRequest);
    }

    public void createRegistrationRequest(RegistrationRequestCreationDTO registrationRequestDTO) {
        checkForExistingEmail(registrationRequestDTO.getUsername());
        checkForExistingPhoneNumber(registrationRequestDTO.getPhoneNumber());

        RegistrationRequest registrationRequest = modelMapper.map(registrationRequestDTO, RegistrationRequest.class);
        registrationRequest.setCreatedAt(LocalDateTime.now());
        registrationRequest.setPasswordHash(passwordEncoder.encode(registrationRequestDTO.getPassword()));
        registrationRequest.setApprovalStatus(ApprovalStatus.PENDING);
        registrationRequestRepository.save(registrationRequest);
    }

    public void createClient(CustomerCreationDTO customerDTO) {
        checkForExistingEmail(customerDTO.getUsername());
        checkForExistingPhoneNumber(customerDTO.getPhoneNumber());

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customer.setEnabled(false);
        customer.setPoints(0);
        customer.setPenalties(0);
        customer.getAuthorities().add(roleRepository.findByName("ROLE_CUSTOMER").orElseThrow());
        customerRepository.save(customer);

        String token = tokenUtils.generateConfirmationToken(customer);
        emailSender.sendRegistrationEmail(customer, token);
    }

    public void createAdmin(AdminCreationDTO dto) {
        checkForExistingEmail(dto.getUsername());

        Administrator administrator = modelMapper.map(dto, Administrator.class);
        administrator.setPassword(passwordEncoder.encode(dto.getPassword()));
        administrator.setEnabled(true);
        administrator.getAuthorities().add(roleRepository.findByName("ROLE_FRESH_ADMIN").orElseThrow());
        userRepository.save(administrator);

        emailSender.sendAdministratorRegistrationEmail(administrator);
    }

    private void checkForExistingRemovalRequest(Long userId) {
        if (removalRequestRepository.findByUserId(userId).isPresent()) {
            RemovalRequest request = removalRequestRepository.findByUserId(userId).get();
            if (request.getApprovalStatus() == ApprovalStatus.PENDING)
                throw new RemovalRequestAlreadyCreatedException("Removal request already submitted.");
        }
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
