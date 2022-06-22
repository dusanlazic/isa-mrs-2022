package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.ComplaintCreationDTO;
import com.team4.isamrs.dto.display.ComplaintDisplayDTO;
import com.team4.isamrs.dto.updation.ComplaintResponseDTO;
import com.team4.isamrs.exception.ActionNotAllowedException;
import com.team4.isamrs.exception.ComplaintAlreadyResolvedException;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.complaint.Complaint;
import com.team4.isamrs.model.enumeration.ResponseStatus;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.AdvertisementRepository;
import com.team4.isamrs.repository.ComplaintRepository;
import com.team4.isamrs.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    public Complaint create(Long id, ComplaintCreationDTO dto, Authentication auth) {
        Customer customer = (Customer) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();

        if (!hasCompletedReservation(customer, advertisement))
            throw new ActionNotAllowedException("You cannot create a complaint as you do not have a completed reservation for this advertisement.");

        Complaint complaint = new Complaint();
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setAdvertisement(advertisement);
        complaint.setCustomer(customer);
        complaint.setComment(dto.getComment());
        complaint.setResponseStatus(ResponseStatus.PENDING);

        return complaintRepository.save(complaint);
    }

    public Collection<ComplaintDisplayDTO> findAllPending() {
        return complaintRepository.findByResponseStatusEquals(ResponseStatus.PENDING)
                .stream().map(complaint -> modelMapper.map(complaint, ComplaintDisplayDTO.class))
                .collect(Collectors.toList());
    }

    public void respondToComplaint(Long id, ComplaintResponseDTO dto) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow();
        if (!complaint.getResponseStatus().equals(ResponseStatus.PENDING))
            throw new ComplaintAlreadyResolvedException();

        complaint.setResponseStatus(ResponseStatus.RESOLVED);

        emailService.sendComplaintResponseToBothParties(complaint, dto, complaint.getAdvertisement(), complaint.getAdvertisement().getAdvertiser(), complaint.getCustomer(), complaint.getCustomer().getAvatar().getStoredFilename());

        complaintRepository.save(complaint);
    }

    private Boolean hasCompletedReservation(Customer customer, Advertisement advertisement) {
        return reservationRepository.existsByAdvertisementEqualsAndCustomerEqualsAndCancelledIsFalseAndEndDateTimeBefore(
                advertisement, customer, LocalDateTime.now());
    }
}
