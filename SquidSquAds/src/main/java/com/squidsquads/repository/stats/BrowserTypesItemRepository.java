package com.squidsquads.repository.stats;

import com.squidsquads.model.stats.BrowserTypesItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrowserTypesItemRepository extends JpaRepository<BrowserTypesItem, Long> {
    List<BrowserTypesItem> findAllByWebsiteIDOrderByRatioDesc(Long websiteID);
}
