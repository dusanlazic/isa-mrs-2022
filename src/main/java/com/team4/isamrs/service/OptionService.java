package com.team4.isamrs.service;

import com.team4.isamrs.model.advertisement.Option;
import com.team4.isamrs.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    public Optional<Option> findById(Long id) {
        return optionRepository.findById(id);
    }
}
