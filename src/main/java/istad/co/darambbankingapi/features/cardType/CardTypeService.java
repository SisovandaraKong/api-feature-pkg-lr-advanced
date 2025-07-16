package istad.co.darambbankingapi.features.cardType;

import istad.co.darambbankingapi.domain.CardType;
import istad.co.darambbankingapi.features.cardType.dto.CardTypeResponse;

import java.util.List;

public interface CardTypeService {
    List<CardTypeResponse> getAllCardTypes();
    CardTypeResponse getCardTypeByName(String name);
}
