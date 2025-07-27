package istad.co.darambbankingapi.features.account;

import istad.co.darambbankingapi.features.account.dto.AccountRename;
import istad.co.darambbankingapi.features.account.dto.AccountRequest;
import istad.co.darambbankingapi.features.account.dto.AccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequest accountRequest){
        accountService.createAccount(accountRequest);
        return new ResponseEntity<>(Map.of(
                "message", "Account created successfully"
        ), HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<?> getAllAccounts(){
//        return new ResponseEntity<>(Map.of(
//                "accounts",accountService.getAllAccounts()
//        ), HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<?> findAccountList(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false,defaultValue = "10") int size){
        Page<AccountResponse> accountPage = accountService.findAllAccounts(page, size);
        return new ResponseEntity<>(Map.of(
                "users",accountPage.getContent(),
                "pages", accountPage.getNumber(),
                "size", accountPage.getSize(),
                "totalPages", accountPage.getTotalPages(),
                "totalElements", accountPage.getTotalElements()
        ), HttpStatus.OK);
    }

    @GetMapping("/{actNo}")
    public ResponseEntity<?> getAccountByActNo(@PathVariable String actNo){
        return new ResponseEntity<>(accountService.getAccountByActNo(actNo), HttpStatus.OK);
    }

    @PutMapping("/{actNo}/rename")
    public ResponseEntity<?> renameAccount(@PathVariable String actNo, @Valid @RequestBody AccountRename accountRename){
        return new ResponseEntity<>(accountService.renameAccount(actNo, accountRename), HttpStatus.OK);
    }

    @PutMapping("/{actNo}/hide")
    public ResponseEntity<?> hideAccount(@PathVariable String actNo){
        accountService.hideAccount(actNo);
        return new ResponseEntity<>(Map.of(
                "message", "Account has been hidden"
        ), HttpStatus.OK);
    }
}
