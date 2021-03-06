package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.updation.RemovalRequestResponseDTO;
import com.team4.isamrs.exception.RegistrationRequestAlreadyResolvedException;
import com.team4.isamrs.exception.RegistrationRequestResponseConflictException;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.user.RemovalRequest;
import com.team4.isamrs.model.user.User;
import com.team4.isamrs.repository.RemovalRequestRepository;
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
public class RemovalRequestService {

    @Autowired
    private RemovalRequestRepository removalRequestRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> Collection<T> findAllPending(Class<T> returnType) {
        return removalRequestRepository.findByApprovalStatusOrderByCreatedAt(ApprovalStatus.PENDING).stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toList());
    }

    @Transactional
    public void respondToRequest(Long id, RemovalRequestResponseDTO dto) {
        RemovalRequest request;
        try {
            request = removalRequestRepository.lockGetById(id).orElseThrow();
        }
        catch (PessimisticLockingFailureException e) {
            throw new RegistrationRequestResponseConflictException(
                    "Another admin is currently attempting to respond to this removal request." +
                            "Try again in a few seconds.");
        }
        if (!request.getApprovalStatus().equals(ApprovalStatus.PENDING))
            throw new RegistrationRequestAlreadyResolvedException();

        if (dto.getApprove()) {
            request.setApprovalStatus(ApprovalStatus.APPROVED);
            disableUser(request.getUser());
            emailService.sendRemovalApprovalEmail(request);
        } else {
            request.setApprovalStatus(ApprovalStatus.REJECTED);
            request.setRejectionReason(dto.getRejectionReason());
            emailService.sendRemovalRejectionEmail(request);
        }
        removalRequestRepository.save(request);
    }

    private void disableUser(User user) {
        user.setEnabled(false);
        userRepository.save(user);
    }
}
