package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.AccountCreationDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Role;
import com.team4.isamrs.model.user.User;
import com.team4.isamrs.repository.PhotoRepository;
import com.team4.isamrs.repository.RoleRepository;
import com.team4.isamrs.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PhotoRepository photoRepository;

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

    public void createTestAccount() {
        Advertiser advertiser = new Advertiser();
        advertiser.setEnabled(true);
        advertiser.setAddress("Kod mene kući BB");
        advertiser.setCity("Novi Sad");
        advertiser.setCountryCode("RS");
        advertiser.setUsername("contact@dusanlazic.com");
        advertiser.setFirstName("Dusan");
        advertiser.setLastName("Lazic");
        advertiser.setPassword(passwordEncoder.encode("13371337"));
        advertiser.getAuthorities().add(roleRepository.findByName("ROLE_FISHING_INSTRUCTOR").get());
        advertiser.setAvatar(photoRepository.getById(UUID.fromString("ac29818c-5e95-438c-85ff-da0a25cd188c")));
        advertiser.setPhoneNumber("065-1337");
        userRepository.save(advertiser);
    }

    public void initializeRoles() {
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_CUSTOMER"));
        roleRepository.save(new Role("ROLE_ADVERTISER"));
        roleRepository.save(new Role("ROLE_FISHING_INSTRUCTOR"));
        roleRepository.save(new Role("ROLE_RESORT_OWNER"));
        roleRepository.save(new Role("ROLE_BOAT_OWNER"));
    }
}
