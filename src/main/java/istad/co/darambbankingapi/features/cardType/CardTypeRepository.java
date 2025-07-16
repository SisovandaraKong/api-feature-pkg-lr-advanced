package istad.co.darambbankingapi.features.cardType;

import istad.co.darambbankingapi.domain.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardTypeRepository extends JpaRepository<CardType, Integer> {
    Optional<CardType> findCardTypeByName(String name);
}
