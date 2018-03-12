package com.squidsquads.repository;

import com.squidsquads.model.VisitsAmountForWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitStatsForWeekRepository extends JpaRepository<VisitsAmountForWeek, Integer> {
    List<VisitsAmountForWeek> findAllByWebsiteID(Integer websiteID);
}
