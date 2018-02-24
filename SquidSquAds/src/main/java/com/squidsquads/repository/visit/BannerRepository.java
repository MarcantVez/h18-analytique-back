package com.squidsquads.repository.visit;

import com.squidsquads.model.account.WebSiteAdmin;
import com.squidsquads.model.traffic.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    List<Banner> findByAccountID(Long accountID);
}
