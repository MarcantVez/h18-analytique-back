package com.squidsquads.repository.visit;

import com.squidsquads.model.traffic.UserAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAgentRepository extends JpaRepository<UserAgent, Long> {

    UserAgent findByUserAgentString(String userAgentStr);

}
