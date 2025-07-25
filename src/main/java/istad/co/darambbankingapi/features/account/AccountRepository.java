package istad.co.darambbankingapi.features.account;

import istad.co.darambbankingapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByActNo(String actNo);
    Optional<Account> findByActNo(String actNo);
}
