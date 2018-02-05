package repository.account;

import model.account.WebSiteAdmin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebSiteAdminRepository extends CrudRepository<WebSiteAdmin, Long> {
    WebSiteAdmin findByAccountID(Long accountID);
}
