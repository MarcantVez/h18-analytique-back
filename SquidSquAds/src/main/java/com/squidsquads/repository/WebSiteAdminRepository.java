package com.squidsquads.repository;

import com.squidsquads.model.WebSiteAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebSiteAdminRepository extends JpaRepository<WebSiteAdmin, Integer> {

    WebSiteAdmin findByAccountID(Integer accountID);

}
