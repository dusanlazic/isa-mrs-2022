package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.updation.RegistrationRequestResponseDTO;
import com.team4.isamrs.exception.RegistrationRequestAlreadyResolvedException;
import com.team4.isamrs.exception.RegistrationRequestResponseConflictException;
import com.team4.isamrs.exception.ReservationConflictException;
import com.team4.isamrs.model.enumeration.AccountType;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.RegistrationRequest;
import com.team4.isamrs.repository.RegistrationRequestRepository;
import com.team4.isamrs.repository.RoleRepository;
import com.team4.isamrs.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class RegistrationRequestService {

    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> Collection<T> findAllPending(Class<T> returnType) {
        return registrationRequestRepository.findByApprovalStatusOrderByCreatedAt(ApprovalStatus.PENDING).stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toList());
    }

    @Transactional
    public void respondToRequest(Long id, RegistrationRequestResponseDTO dto) {
        RegistrationRequest request;
        try {
            request = registrationRequestRepository.lockGetById(id).orElseThrow();
        }
        catch (PessimisticLockingFailureException e) {
            throw new RegistrationRequestResponseConflictException(
                    "Another admin is currently attempting to respond to this request. Try again in a few seconds.");
        }
        if (!request.getApprovalStatus().equals(ApprovalStatus.PENDING))
            throw new RegistrationRequestAlreadyResolvedException();

        if (dto.getApprove()) {
            request.setApprovalStatus(ApprovalStatus.APPROVED);
            createAdvertiser(request);
            emailService.sendRegistrationApprovalEmail(request);
        } else {
            request.setApprovalStatus(ApprovalStatus.REJECTED);
            request.setRejectionReason(dto.getRejectionReason());
            emailService.sendRegistrationRejectionEmail(request);
        }
        registrationRequestRepository.save(request);
    }

    public void createAdvertiser(RegistrationRequest registrationRequest) {
        Advertiser advertiser = new Advertiser();
        advertiser.setFirstName(registrationRequest.getFirstName());
        advertiser.setLastName(registrationRequest.getLastName());
        advertiser.setUsername(registrationRequest.getUsername());
        advertiser.setAddress(registrationRequest.getAddress());
        advertiser.setCity(registrationRequest.getCity());
        advertiser.setCountryCode(registrationRequest.getCountry());
        advertiser.setPhoneNumber(registrationRequest.getPhoneNumber());
        advertiser.setPassword(registrationRequest.getPasswordHash());
        advertiser.setEnabled(true);
        advertiser.setPoints(0);

        if (registrationRequest.getAccountType().equals(AccountType.BOAT_OWNER))
            advertiser.getAuthorities().add(roleRepository.findByName("ROLE_BOAT_OWNER").orElseThrow());
        else if (registrationRequest.getAccountType().equals(AccountType.RESORT_OWNER))
            advertiser.getAuthorities().add(roleRepository.findByName("ROLE_RESORT_OWNER").orElseThrow());
        else if (registrationRequest.getAccountType().equals(AccountType.FISHING_INSTRUCTOR))
            advertiser.getAuthorities().add(roleRepository.findByName("ROLE_FISHING_INSTRUCTOR").orElseThrow());

        userRepository.save(advertiser);
    }
}
