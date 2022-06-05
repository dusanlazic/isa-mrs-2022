package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.ReservationReportCreationDTO;
import com.team4.isamrs.dto.display.ReservationReportDisplayDTO;
import com.team4.isamrs.exception.ReservationIsNotCompletedException;
import com.team4.isamrs.exception.ReservationReportAlreadyExistsException;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.reservation.ReservationReport;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.CustomerRepository;
import com.team4.isamrs.repository.ReservationReportRepository;
import com.team4.isamrs.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class ReservationReportService {

    @Autowired
    private ReservationReportRepository reservationReportRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

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
            customer.setPoints(customer.getPoints() + 1);
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
}
