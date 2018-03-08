package com.squidsquads.repository;

import com.squidsquads.model.BrowserTypesItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrowserTypesItemRepository extends JpaRepository<BrowserTypesItem, Integer> {

    List<BrowserTypesItem> findAllByWebsiteIDOrderByRatioDesc(Integer websiteID);

}
