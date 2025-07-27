package istad.co.darambbankingapi.features.account;

import istad.co.darambbankingapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByActNo(String actNo);
    Optional<Account> findByActNo(String actNo);
    @Modifying
    @Query("UPDATE Account as a SET a.isHidden = TRUE WHERE a.actNo = ?1")
    void hideAccount(String actNo);
}
