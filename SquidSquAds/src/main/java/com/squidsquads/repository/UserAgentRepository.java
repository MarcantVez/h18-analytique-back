package com.squidsquads.repository;

import com.squidsquads.model.UserAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAgentRepository extends JpaRepository<UserAgent, Integer> {

    UserAgent findByUserAgentString(String userAgentStr);

}
