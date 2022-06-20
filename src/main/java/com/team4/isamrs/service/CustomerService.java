package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.*;
import com.team4.isamrs.model.advertisement.AdventureAd;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.BoatAd;
import com.team4.isamrs.model.advertisement.ResortAd;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.AdvertisementRepository;
import com.team4.isamrs.repository.CustomerRepository;
import com.team4.isamrs.repository.ReservationRepository;
import com.team4.isamrs.repository.ReviewRepository;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return modelMapper.map(customer, returnType);
    }

    public Collection<ReviewPublicDisplayDTO> getReviews(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return reviewRepository.findByCustomer(customer).stream()
                .map(e -> modelMapper.map(e, ReviewPublicDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public Page<ReservationSimpleDisplayDTO> getPreviousReservations(Long id, Pageable pageable) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return reservationRepository.findReservationsByCustomerEqualsAndStartDateTimeBefore(customer, LocalDateTime.now(), pageable)
                .map(reservation -> {
                    ReservationSimpleDisplayDTO dto = new ReservationSimpleDisplayDTO();
                    dto.setCustomer(modelMapper.map(customer, CustomerSimpleDisplayDTO.class));
                    dto.setStartDateTime(reservation.getStartDateTime());
                    dto.setEndDateTime(reservation.getEndDateTime());
                    dto.setCancelled(reservation.getCancelled());
                    dto.setCalculatedPrice(reservation.getCalculatedPrice());
                    dto.setId(reservation.getId());
                    dto.setAttendees(reservation.getAttendees());
                    dto.setCreatedAt(reservation.getCreatedAt());

                    // domain mapper sucks
                    Advertisement advertisement = (Advertisement) Hibernate.unproxy(reservation.getAdvertisement());
                    if (advertisement instanceof ResortAd) {
                        ResortAd concreteAd = (ResortAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }
                    else if (advertisement instanceof BoatAd) {
                        BoatAd concreteAd = (BoatAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }
                    else if (advertisement instanceof AdventureAd) {
                        AdventureAd concreteAd = (AdventureAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }

                    return dto;
                });
    }

    public Page<ReservationSimpleDisplayDTO> getUpcomingReservations(Long id, PageRequest pageable) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return reservationRepository.findReservationsByCustomerEqualsAndStartDateTimeAfter(customer, LocalDateTime.now(), pageable)
                .map(reservation -> {
                    ReservationSimpleDisplayDTO dto = new ReservationSimpleDisplayDTO();
                    dto.setCustomer(modelMapper.map(customer, CustomerSimpleDisplayDTO.class));
                    dto.setStartDateTime(reservation.getStartDateTime());
                    dto.setEndDateTime(reservation.getEndDateTime());
                    dto.setCancelled(reservation.getCancelled());
                    dto.setCalculatedPrice(reservation.getCalculatedPrice());
                    dto.setId(reservation.getId());
                    dto.setAttendees(reservation.getAttendees());
                    dto.setCreatedAt(reservation.getCreatedAt());

                    // domain mapper sucks
                    Advertisement advertisement = (Advertisement) Hibernate.unproxy(reservation.getAdvertisement());
                    if (advertisement instanceof ResortAd) {
                        ResortAd concreteAd = (ResortAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }
                    else if (advertisement instanceof BoatAd) {
                        BoatAd concreteAd = (BoatAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }
                    else if (advertisement instanceof AdventureAd) {
                        AdventureAd concreteAd = (AdventureAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }

                    return dto;
                });
    }
}
