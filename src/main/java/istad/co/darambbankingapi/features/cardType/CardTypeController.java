package istad.co.darambbankingapi.features.cardType;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/card-types")
@RequiredArgsConstructor
public class CardTypeController {
    private final CardTypeService cardTypeService;

    @GetMapping
    public ResponseEntity<?> getAllCardTypes() {
        return new ResponseEntity<>(
                Map.of(
                        "cardTypes",cardTypeService.getAllCardTypes()
                ), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCardTypeByName(@PathVariable String name) {
        return new ResponseEntity<>(cardTypeService.getCardTypeByName(name), HttpStatus.OK);
    }
}
