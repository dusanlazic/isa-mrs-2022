package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c.subscriptions FROM Customer c WHERE c = :customer")
    Set<Advertisement> getSubscriptionsByCustomer(Customer customer);
}
