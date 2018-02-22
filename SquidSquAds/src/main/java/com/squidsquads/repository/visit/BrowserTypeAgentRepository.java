package com.squidsquads.repository.visit;


import com.squidsquads.model.traffic.BrowserTypeAgent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrowserTypeAgentRepository extends CrudRepository<BrowserTypeAgent, Long> {
    List<BrowserTypeAgent> findAllByBrowserTypeId (Long browserTypeId);
    List<BrowserTypeAgent> findAllByUserAgentId (Long userAgentId);
}
