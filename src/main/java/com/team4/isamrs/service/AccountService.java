package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.AccountCreationDTO;
import com.team4.isamrs.dto.creation.OptionCreationDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.Option;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.model.user.User;
import com.team4.isamrs.repository.CustomerRepository;
import com.team4.isamrs.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AccountService {

    // customer repo at the moment, user repo does not work
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        return modelMapper.map(customerRepository.findById(id).orElseThrow(), returnType);
    }

    public void updateAccount(AccountCreationDTO dto) {
        // id will be taken from the token after auth is implemented
        Customer customer = customerRepository.findById(dto.getId()).orElseThrow();
        dto.setEmailAddress(customer.getEmailAddress()); // email stays the same
        modelMapper.map(dto, customer);
        customerRepository.save(customer);
    }
}
