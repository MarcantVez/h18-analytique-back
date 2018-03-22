package com.squidsquads.repository;

import com.squidsquads.model.Royalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoyaltyRepository extends JpaRepository<Royalty, Integer> {
    Royalty findByVisitID(Integer visitID);
}
