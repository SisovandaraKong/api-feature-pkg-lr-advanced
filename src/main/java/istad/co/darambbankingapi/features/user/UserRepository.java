package istad.co.darambbankingapi.features.user;


import istad.co.darambbankingapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUuid(String uuid);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalCardId(String nationalCardId);
    boolean existsByStudentCardId(String studentCardId);

    boolean existsByUuid(String uuid);

    @Modifying
    @Query("UPDATE User AS u SET u.isBlocked = TRUE WHERE u.uuid = ?1")
    void blockByUuid(String uuid);

    void deleteByUuid(String uuid);

    @Modifying
    @Query("UPDATE User AS u SET u.isDeleted = TRUE WHERE u.uuid = ?1")
    void disableByUuid(String uuid);

    @Modifying
    @Query("UPDATE User AS u SET u.isDeleted = FALSE WHERE u.uuid = ?1")
    void enableByUuid(String uuid);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
