package istad.co.darambbankingapi.features.accountType;

import istad.co.darambbankingapi.features.accountType.dto.AccountTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    @GetMapping
    public ResponseEntity<?> getAllAccountTypes() {
        return new ResponseEntity<>(
                Map.of(
                        "accountTypes", accountTypeService.getAllAccountTypes()
                ), HttpStatus.OK

        );
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getAccountTypeByName(@PathVariable String name) {
        return new ResponseEntity<>(accountTypeService.getAccountTypeByName(name), HttpStatus.OK);
    }
}
