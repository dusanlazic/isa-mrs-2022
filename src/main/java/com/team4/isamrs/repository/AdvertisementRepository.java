package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.Set;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Optional<Advertisement> findAdvertisementByIdAndAdvertiser(Long id, Advertiser advertiser);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.approvalStatus = 1 AND r.advertisement = ?1")
    Optional<Double> findAverageRatingForAdvertisement(Advertisement advertisement);

    Page<Advertisement> findAdvertisementsByAdvertiser(Advertiser advertiser, Pageable pageable);

    @Query("SELECT c FROM Customer c JOIN c.subscriptions s WHERE s = ?1")
    Set<Customer> getSubscribersOfAdvertisement(Advertisement advertisement);
}
