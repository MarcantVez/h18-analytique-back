package com.squidsquads.repository.site;

import com.squidsquads.model.site.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

    List<Site> findByUserProfileID(Long userProfileID);

}
