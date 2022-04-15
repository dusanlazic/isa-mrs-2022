package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.ResortAdDisplayDTO;
import com.team4.isamrs.model.resort.ResortAd;
import com.team4.isamrs.repository.ResortAdRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ResortAdService {

    @Autowired
    private ResortAdRepository resortAdRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Collection<ResortAdDisplayDTO> findAll() {
        return resortAdRepository.findAll().stream()
                .map(e -> modelMapper.map(e, ResortAdDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public ResortAdDisplayDTO findById(Long id) {
        ResortAd resortAd = resortAdRepository.findById(id).orElseThrow();
        return modelMapper.map(resortAd, ResortAdDisplayDTO.class);
    }
}
