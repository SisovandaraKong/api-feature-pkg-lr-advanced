package istad.co.darambbankingapi.features.account;

import istad.co.darambbankingapi.domain.Account;
import istad.co.darambbankingapi.domain.AccountType;
import istad.co.darambbankingapi.domain.User;
import istad.co.darambbankingapi.domain.UserAccount;
import istad.co.darambbankingapi.features.account.dto.AccountRename;
import istad.co.darambbankingapi.features.account.dto.AccountRequest;
import istad.co.darambbankingapi.features.account.dto.AccountResponse;
import istad.co.darambbankingapi.features.accountType.AccountTypeRepository;
import istad.co.darambbankingapi.features.user.UserRepository;
import istad.co.darambbankingapi.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserAccountRepository userAccountRepository;
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    @Override
    public void createAccount(AccountRequest accountRequest) {

        AccountType accountType = accountTypeRepository.findAccountTypeByName(accountRequest.accountTypeName())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Invalid account type"));

        User user = userRepository.findByUuid(accountRequest.userUuid())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Invalid user's uuid"));

        Account account = accountMapper.fromAccountRequest(accountRequest);
        account.setAccountType(accountType);
        account.setActName(user.getName());
        String actNo;
        do {
            actNo = String.format("%09d", new Random().nextInt(1_000_000_000));
        } while (accountRepository.existsByActNo(actNo));
        account.setActNo(actNo);
        account.setTransferLimit(BigDecimal.valueOf(5000));
        account.setIsHidden(false);

        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setUser(user);
        userAccount.setIsDeleted(false);
        userAccount.setIsBlocked(false);
        userAccount.setCreatedAt(LocalDateTime.now());
        userAccountRepository.save(userAccount);
    }

    @Override
    public AccountResponse getAccountByActNo(String actNo) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account Number is not found"));
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public Page<AccountResponse> findAllAccounts(int page, int size) {
        if (page < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Page must be greater than zero or equal to zero");
        }
        if (size < 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Size must be greater than 1 or equal to 1");
        }

        Sort sortByActName = Sort.by(Sort.Direction.ASC, "actName");
        PageRequest pageRequest = PageRequest.of(page, size, sortByActName);
        Page<Account> accounts = accountRepository.findAll(pageRequest);
        return accounts.map(accountMapper::toAccountResponse);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    public AccountResponse renameAccount(String actNo, AccountRename accountRename) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account Number is not found"));

        // Check if the same name
        if (accountRename.newName().equals(account.getAlias())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Account name already exists");
        }
        account.setAlias(accountRename.newName());
        account = accountRepository.save(account);
        return accountMapper.toAccountResponse(account);
    }

    @Transactional
    @Override
    public void hideAccount(String actNo) {
        if (!accountRepository.existsByActNo(actNo)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account Number is not found");
        }
       try {
           accountRepository.hideAccount(actNo);
       }catch (Exception e){
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                   "Something went wrong hiding the account");
       }
    }
}
