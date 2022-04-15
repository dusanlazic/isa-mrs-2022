package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.model.review.ServiceReview;
import com.team4.isamrs.repository.ServiceReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ServiceReviewService {
    @Autowired
    private ServiceReviewRepository serviceReviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Collection<ServiceReviewDisplayDTO> findAll() {
        return serviceReviewRepository.findAll().stream()
                .map(e -> modelMapper.map(e, ServiceReviewDisplayDTO.class))
                    .collect(Collectors.toSet());
    }

    public ServiceReviewDisplayDTO findById(Long id) {
        ServiceReview serviceReview = serviceReviewRepository.findById(id).orElseThrow();
        return modelMapper.map(serviceReview, ServiceReviewDisplayDTO.class);
    }
}
