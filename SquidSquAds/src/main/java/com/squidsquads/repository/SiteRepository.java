package com.squidsquads.repository;

import com.squidsquads.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {

    List<Site> findByUserProfileID(Integer userProfileID);

}
