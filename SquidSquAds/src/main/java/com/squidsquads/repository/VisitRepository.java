package com.squidsquads.repository;

import com.squidsquads.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {
    Visit findByBannerID(Integer BannerID);
    Visit findByVisitID(Integer VisitID);
}
