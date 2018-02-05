package repository;

import model.Campaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

@Repository
public interface CampaignRepository extends CrudRepository<Campaign, Long> {
    List<Campaign> findByAuthor (Long accountId);
}