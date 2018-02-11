package com.squidsquads.repository.site;

import com.squidsquads.model.profile.UserProfile;
import com.squidsquads.model.site.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-11
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

    Site findBySiteID( Long siteID);

    Site findByUserProfileID(Long userProfileID);
}
