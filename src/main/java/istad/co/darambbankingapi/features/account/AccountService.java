package istad.co.darambbankingapi.features.account;

import istad.co.darambbankingapi.features.account.dto.AccountRename;
import istad.co.darambbankingapi.features.account.dto.AccountRequest;
import istad.co.darambbankingapi.features.account.dto.AccountResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    void createAccount(AccountRequest accountRequest);
    AccountResponse getAccountByActNo(String actNo);
    Page<AccountResponse> findAllAccounts(int page, int size);
    List<AccountResponse> getAllAccounts();
    AccountResponse renameAccount(String actNo,AccountRename accountRename);
    void hideAccount(String actNo);
}

