package com.squidsquads.repository.visit;

import com.squidsquads.model.traffic.BrowserType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrowserTypeRepository extends CrudRepository<BrowserType,Long> {
    BrowserType findByName(String browserTypeName);
}
