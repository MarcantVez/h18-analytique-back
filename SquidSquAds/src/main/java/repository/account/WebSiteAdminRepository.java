package repository.account;

import model.account.WebSiteAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebSiteAdminRepository extends JpaRepository<WebSiteAdmin, Long> {

    WebSiteAdmin findByAccountID(Long accountID);

}
