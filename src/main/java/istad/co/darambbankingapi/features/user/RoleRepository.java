package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.domain.Role;
import istad.co.darambbankingapi.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName name);
}
