package com.squidsquads.repository.account;

import com.squidsquads.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

    // TUTORIAL
    // https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-introduction-to-query-methods/
//    @Query(value = "SELECT * FROM compte_utilisateur WHERE numero_compte = :id", nativeQuery=true)
//    Account customFind(@Param("id") Long id);
}
