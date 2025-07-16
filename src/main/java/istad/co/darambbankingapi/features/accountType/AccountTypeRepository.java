package istad.co.darambbankingapi.features.accountType;

import istad.co.darambbankingapi.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
    Optional<AccountType> findAccountTypeByName(String name);
}
