package com.squidsquads.repository.userProfile;

import com.squidsquads.model.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    List<UserProfile> findByAccountID(Long accountID);

    UserProfile findByAccountIDAndProfileID(Long accountID, Long userProfileID);

    UserProfile findByNameAndAccountID(String name, Long accountID);

    UserProfile findByProfileIDAndAccountID(Long profileID, Long accountID);
}
