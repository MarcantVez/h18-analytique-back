package repository.userProfile;

import model.userProfile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-05
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@Repository
public interface UserProfileRepository extends JpaRepository< UserProfile, Long> {

    UserProfile findByName(String name);

    UserProfile findByProfileID(Long profileID);
}
