package com.squidsquads.repository.visit;

import com.squidsquads.model.traffic.UserAgent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAgentRepository  extends CrudRepository<UserAgent, Long> {
    UserAgent findByUserAgentString(String userAgentStr);
}
