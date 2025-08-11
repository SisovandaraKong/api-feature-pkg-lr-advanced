package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
