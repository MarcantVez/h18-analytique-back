package com.squidsquads.repository;

import com.squidsquads.model.VisitsAmountForMonth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitStatsForMonthRepository extends JpaRepository<VisitsAmountForMonth, Integer> {
    List<VisitsAmountForMonth> findAllByWebsiteID(Integer websiteID);
}
