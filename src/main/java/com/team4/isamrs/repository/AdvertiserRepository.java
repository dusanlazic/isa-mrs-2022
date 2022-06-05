package com.team4.isamrs.repository;

import com.team4.isamrs.model.user.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdvertiserRepository extends JpaRepository<Advertiser, Long> {

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.approvalStatus = 1 AND r.advertisement.advertiser = ?1")
    Optional<Double> findAverageRatingForAdvertiser(Advertiser advertiser);
}