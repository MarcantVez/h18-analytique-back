package com.squidsquads.repository;

import com.squidsquads.model.VisitsAmountFor24h;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitStatsForDayRepository extends JpaRepository<VisitsAmountFor24h, Integer> {
    List<VisitsAmountFor24h> findAllByWebsiteID(Integer websiteID);
}
