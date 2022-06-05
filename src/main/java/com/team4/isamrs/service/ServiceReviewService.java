package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.model.review.Review;
import com.team4.isamrs.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ServiceReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Collection<ServiceReviewDisplayDTO> findAll() {
        return reviewRepository.findAll().stream()
                .map(e -> modelMapper.map(e, ServiceReviewDisplayDTO.class))
                    .collect(Collectors.toSet());
    }

    public ServiceReviewDisplayDTO findById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        return modelMapper.map(review, ServiceReviewDisplayDTO.class);
    }
}
