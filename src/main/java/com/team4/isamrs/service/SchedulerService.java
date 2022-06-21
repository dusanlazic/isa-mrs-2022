package com.team4.isamrs.service;

import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SchedulerService {

    @Autowired
    CustomerRepository customerRepository;

    @Scheduled(cron = "0 0 2 1 * ?", zone="Europe/Berlin")
    public void resetPenalties() {
        Collection<Customer> customers = customerRepository.findAll();
        customers.forEach(customer -> customer.setPenalties(0));
        customerRepository.saveAll(customers);
    }
}
