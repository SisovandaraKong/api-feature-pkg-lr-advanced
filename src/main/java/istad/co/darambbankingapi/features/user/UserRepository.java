package istad.co.darambbankingapi.features.user;


import istad.co.darambbankingapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
