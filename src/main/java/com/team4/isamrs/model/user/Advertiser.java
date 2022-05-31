package com.team4.isamrs.model.user;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.review.ServiceProviderReview;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Advertiser extends User {
    /*
    User that can have ads (resort owner/boat owner/fishing instructor)
     */
    @OneToMany(mappedBy = "advertiser", fetch = FetchType.LAZY)
    private Set<Advertisement> ads = new HashSet<Advertisement>();

    @OneToMany(mappedBy = "advertiser", fetch = FetchType.LAZY)
    private Set<ServiceProviderReview> reviews = new HashSet<ServiceProviderReview>();

    @Column(name = "points", nullable = false)
    private Integer points;
}