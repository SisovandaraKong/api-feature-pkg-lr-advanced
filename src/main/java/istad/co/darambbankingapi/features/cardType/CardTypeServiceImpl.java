package istad.co.darambbankingapi.features.cardType;

import istad.co.darambbankingapi.domain.CardType;
import istad.co.darambbankingapi.features.cardType.dto.CardTypeResponse;
import istad.co.darambbankingapi.mapper.CardTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardTypeServiceImpl implements CardTypeService {
    private final CardTypeRepository cardTypeRepository;
    private final CardTypeMapper cardTypeMapper;

    @Override
    public List<CardTypeResponse> getAllCardTypes() {
        List<CardType> cardTypes = cardTypeRepository.findAll();
        return cardTypes
                .stream()
                .map(cardTypeMapper::toCardTypeResponse)
                .toList();
    }

    @Override
    public CardTypeResponse getCardTypeByName(String name) {
        CardType cardType = cardTypeRepository.findCardTypeByName(name)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Card type's name not found"));
        return cardTypeMapper.toCardTypeResponse(cardType);
    }
}
