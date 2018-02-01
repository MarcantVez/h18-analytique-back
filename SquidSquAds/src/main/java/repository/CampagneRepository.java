package repository;

import model.Campagne;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Marc-Antoine Vézina
 * @Date_Of_Creation: 2018-02-01
 */

@Repository
public interface CampagneRepository extends org.springframework.data.repository.Repository<Campagne, Long> {
    List<Campagne> findAllByNumeroCompte(int numeroCompte);
}