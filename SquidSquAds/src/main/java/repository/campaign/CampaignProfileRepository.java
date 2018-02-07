package repository.campaign;

import model.CampaignProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignProfileRepository extends CrudRepository<CampaignProfile, Long> {
}
