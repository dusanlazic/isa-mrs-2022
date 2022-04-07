package com.team4.isamrs.model.entity.user;

import com.team4.isamrs.model.entity.advertisement.Advertisement;
import com.team4.isamrs.model.entity.reservation.Reservation;
import com.team4.isamrs.model.entity.review.ServiceReview;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer extends User {
    /*
    Users that make reservations
     */
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<Reservation>();

    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"))
    private Set<Advertisement> subscriptions = new HashSet<Advertisement>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<ServiceReview> reviews = new HashSet<ServiceReview>();
}
