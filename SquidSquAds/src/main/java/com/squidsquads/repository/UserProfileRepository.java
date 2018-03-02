package com.squidsquads.repository;

import com.squidsquads.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    List<UserProfile> findByAccountID(Integer accountID);

    UserProfile findByAccountIDAndProfileID(Integer accountID, Integer userProfileID);

    UserProfile findByNameAndAccountID(String name, Integer accountID);

    UserProfile findByProfileIDAndAccountID(Integer profileID, Integer accountID);
}
