package com.squidsquads.repository;
import com.squidsquads.model.RoyaltyAmountStat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoyaltyStatsRepository extends JpaRepository<RoyaltyAmountStat, Integer> {
    List<RoyaltyAmountStat> findAllByCompte(Integer compteID);
}
