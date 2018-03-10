package com.squidsquads.repository;

import com.squidsquads.model.VisitsAmountForYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitStatsForYearRepository extends JpaRepository<VisitsAmountForYear, Integer> {
    List<VisitsAmountForYear> findAllByWebsiteID(Integer websiteID);
}
