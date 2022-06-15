package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.user.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Optional<Advertisement> findAdvertisementByIdAndAdvertiser(Long id, Advertiser advertiser);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.approvalStatus = 1 AND r.advertisement = ?1")
    Optional<Double> findAverageRatingForAdvertisement(Advertisement advertisement);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Advertisement a WHERE a.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Optional<Advertisement> findOneById(Long id);
}
