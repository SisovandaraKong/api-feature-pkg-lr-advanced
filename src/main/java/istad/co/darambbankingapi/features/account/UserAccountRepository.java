package istad.co.darambbankingapi.features.account;

import istad.co.darambbankingapi.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

}
