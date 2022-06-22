package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.ReservationReportCreationDTO;
import com.team4.isamrs.dto.display.ReservationReportDisplayDTO;
import com.team4.isamrs.dto.updation.ReservationReportResponseDTO;
import com.team4.isamrs.exception.ReservationIsNotCompletedException;
import com.team4.isamrs.exception.ReservationReportAlreadyExistsException;
import com.team4.isamrs.exception.ReservationReportAlreadyResolvedException;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.reservation.ReservationReport;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.CustomerRepository;
import com.team4.isamrs.repository.ReservationReportRepository;
import com.team4.isamrs.repository.ReservationRepository;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReservationReportService {

    @Autowired
    private ReservationReportRepository reservationReportRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    public void create(Long reservationId, ReservationReportCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        if (!reservation.getAdvertisement().getAdvertiser().getUsername().equals(advertiser.getUsername()))
            throw new NoSuchElementException();

        if (reservation.getEndDateTime().isAfter(LocalDateTime.now()))
            throw new ReservationIsNotCompletedException();

        if (reservationReportRepository.existsReservationReportByReservation(reservation))
            throw new ReservationReportAlreadyExistsException();

        ReservationReport report = modelMapper.map(dto, ReservationReport.class);
        report.setCreatedAt(LocalDateTime.now());
        report.setReservation(reservation);

        if (dto.getPenaltyRequested())
            report.setApprovalStatus(ApprovalStatus.PENDING);
        else
            report.setApprovalStatus(ApprovalStatus.APPROVED);

        if (dto.getCustomerWasLate()) {
            Customer customer = reservation.getCustomer();
            customer.setPenalties(customer.getPenalties() + 1);
            customerRepository.save(customer);
        }

        reservationReportRepository.save(report);
    }

    public ReservationReportDisplayDTO findForReservation(Long id, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        if (!reservation.getAdvertisement().getAdvertiser().getUsername().equals(advertiser.getUsername()))
            throw new NoSuchElementException();

        ReservationReport report = reservationReportRepository.findByReservation(reservation).orElseThrow();
        return modelMapper.map(report, ReservationReportDisplayDTO.class);
    }

    public Collection<ReservationReportDisplayDTO> findAllPending() {
        return reservationReportRepository.findByApprovalStatusEquals(ApprovalStatus.PENDING)
                .stream().map(report -> modelMapper.map(report, ReservationReportDisplayDTO.class))
                .collect(Collectors.toList());
    }

    public void respondToReport(Long id, ReservationReportResponseDTO dto) {
        ReservationReport report = reservationReportRepository.findById(id).orElseThrow();
        if (!report.getApprovalStatus().equals(ApprovalStatus.PENDING))
            throw new ReservationReportAlreadyResolvedException();

        if (dto.getApprove()) {
            Customer customer = report.getReservation().getCustomer();
            customer.setPenalties(customer.getPenalties() + 1);
            report.setApprovalStatus(ApprovalStatus.APPROVED);
            customerRepository.save(customer);
            emailService.sendReportApprovalEmail(report, report.getReservation().getAdvertisement(), report.getReservation().getAdvertisement().getAdvertiser(), (Customer) Hibernate.unproxy(report.getReservation().getCustomer()));
        } else {
            report.setApprovalStatus(ApprovalStatus.REJECTED);
            emailService.sendReportRejectionEmail(report, report.getReservation().getAdvertisement(), report.getReservation().getAdvertisement().getAdvertiser(), (Customer) Hibernate.unproxy(report.getReservation().getCustomer()));
        }

        reservationReportRepository.save(report);
    }
}
