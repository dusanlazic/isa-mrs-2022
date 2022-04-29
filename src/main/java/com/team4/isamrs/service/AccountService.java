package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.AccountCreationDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.model.user.User;
import com.team4.isamrs.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(), returnType);
    }

    public void updateAccount(AccountCreationDTO dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow();
        dto.setUsername(user.getUsername()); // email stays the same
        modelMapper.map(dto, user);
        userRepository.save(user);
    }
}
