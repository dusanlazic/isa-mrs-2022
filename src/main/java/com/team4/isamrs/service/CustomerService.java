package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.boat.BoatAd;
import com.team4.isamrs.model.review.ServiceReview;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return modelMapper.map(customer, returnType);
    }

    public Set<ServiceReviewDisplayDTO> getReviews(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return customer.getReviews().stream()
                .map(e -> modelMapper.map(e, ServiceReviewDisplayDTO.class))
                .collect(Collectors.toSet());
    }
}
