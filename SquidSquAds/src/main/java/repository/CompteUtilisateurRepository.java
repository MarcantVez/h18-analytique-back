package repository;

import model.CompteUtilisateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteUtilisateurRepository extends CrudRepository<CompteUtilisateur, Long> {
}
