package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.BoatAd;
import com.team4.isamrs.model.user.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoatAdRepository extends JpaRepository<BoatAd, Long> {
    Optional<BoatAd> findBoatAdByIdAndAdvertiser(Long id, Advertiser advertiser);

    @Query("SELECT bad FROM BoatAd bad JOIN Address a ON bad.address = a.id WHERE" +
            "(CAST(:guests AS string) IS NULL OR bad.capacity >= :guests) AND " +
            "(CAST(:where AS string) IS NULL " +
            "OR LOWER(a.city) LIKE LOWER(concat('%', :where, '%'))" +
            "OR LOWER(a.state) LIKE LOWER(concat('%', :where, '%')))")
    List<BoatAd> search(
            @Param("where") String where,
            @Param("guests") int guests);

}
