package com.team4.isamrs.service;

import com.team4.isamrs.model.advertisement.HourlyPrice;
import com.team4.isamrs.repository.HourlyPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HourlyPriceService {

    @Autowired
    private HourlyPriceRepository hourlyPriceRepository;

    public Optional<HourlyPrice> findById(Long id) { return hourlyPriceRepository.findById(id); }
}
