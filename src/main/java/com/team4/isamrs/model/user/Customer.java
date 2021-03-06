package com.team4.isamrs.model.user;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.reservation.Reservation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer extends User {
    /*
    Users that make reservations
     */
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"))
    private Set<Advertisement> subscriptions = new HashSet<>();
    
    @Column(name = "points", nullable = false)
    private Integer points;

    @Column(name = "penalties", nullable = false)
    private Integer penalties;
}
