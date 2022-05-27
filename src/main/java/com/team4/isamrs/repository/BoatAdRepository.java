package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.BoatAd;
import com.team4.isamrs.model.user.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoatAdRepository extends JpaRepository<BoatAd, Long> {
    Optional<BoatAd> findBoatAdByIdAndAdvertiser(Long id, Advertiser advertiser);
}
