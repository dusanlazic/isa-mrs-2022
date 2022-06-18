package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.user.Advertiser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;
import java.util.stream.DoubleStream;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Optional<Advertisement> findAdvertisementByIdAndAdvertiser(Long id, Advertiser advertiser);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.approvalStatus = 1 AND r.advertisement = ?1")
    Optional<Double> findAverageRatingForAdvertisement(Advertisement advertisement);

    Page<Advertisement> findAdvertisementsByAdvertiser(Advertiser advertiser, Pageable pageable);
}
