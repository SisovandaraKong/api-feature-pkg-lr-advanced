package istad.co.darambbankingapi.mapper;

import istad.co.darambbankingapi.domain.CardType;
import istad.co.darambbankingapi.features.cardType.dto.CardTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardTypeMapper {
    CardTypeResponse toCardTypeResponse(CardType cardType);
}
