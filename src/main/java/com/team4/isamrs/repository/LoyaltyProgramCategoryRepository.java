package com.team4.isamrs.repository;

import com.team4.isamrs.model.loyalty.LoyaltyProgramCategory;
import com.team4.isamrs.model.loyalty.TargetedAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface LoyaltyProgramCategoryRepository extends JpaRepository<LoyaltyProgramCategory, Long> {
    Boolean existsByTitleAndTargetedAccountType(String title, TargetedAccountType targetedAccountType);
    Collection<LoyaltyProgramCategory> findByTargetedAccountType(TargetedAccountType targetedAccountType);

    @Query("SELECT c FROM LoyaltyProgramCategory c WHERE c.pointsLowerBound <= ?1 AND c.pointsUpperBound >= ?1 AND c.targetedAccountType = ?2")
    Optional<LoyaltyProgramCategory> findByPointsAndAccountType(Integer points, TargetedAccountType targetedAccountType);
}
