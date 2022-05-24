package com.team4.isamrs.service;

import com.team4.isamrs.model.advertisement.Address;
import com.team4.isamrs.model.resort.ResortAd;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.AdventureAdRepository;
import com.team4.isamrs.repository.BoatAdRepository;
import com.team4.isamrs.repository.ResortAdRepository;
import com.team4.isamrs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class TestDataSupplierService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ResortAdRepository resortAdRepository;

    @Autowired
    BoatAdRepository boatAdRepository;

    @Autowired
    AdventureAdRepository adventureAdRepository;

    public void injectTestData() {
        addResorts();
    }

    private void addResorts() {
        for (int i = 0; i < 20; i++) {
            ResortAd resort = new ResortAd();
            resort.setAdvertiser((Advertiser) userRepository.findByUsername("maja@gmail.com").get());
            resort.setTitle("Example Resort " + i);

            Address address = new Address();
            address.setAddress("Example Address " + i);
            address.setCity(i % 2 == 0 ? "Novi Sad" : "Zrenjanin");
            address.setCountryCode("RS");
            address.setLatitude("45.313108");
            address.setLongitude("20.446850");
            address.setState("Vojvodina");
            address.setPostalCode(i % 2 == 0 ? "21000" : "23000");
            resort.setAddress(address);

            resort.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                    "Maecenas fringilla metus nec justo tempus venenatis. Etiam ut neque eget ipsum.");
            resort.setPricingDescription("200");
            resort.setAvailableAfter(LocalDateTime.now());
            resort.setAvailableUntil(LocalDateTime.now().plusMonths(3));
            resort.setRules("No smoking!");
            resort.setCurrency("€");
            resort.setNumberOfBeds(Long.toString(i % 3 + 2));
            resort.setCheckOutTime(LocalTime.parse("10:00"));
            resort.setCheckInTime(LocalTime.parse("14:00"));

            resortAdRepository.save(resort);
        }
    }
}
