package com.squidsquads.repository;
import com.squidsquads.model.RoyaltyAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoyaltyStatsRepository extends JpaRepository<RoyaltyAmount, Integer> {
    List<RoyaltyAmount> findAllByCompte(Integer compteID);
}
