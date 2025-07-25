package istad.co.darambbankingapi.features.account;

import istad.co.darambbankingapi.features.account.dto.AccountRequest;
import istad.co.darambbankingapi.features.account.dto.AccountResponse;

import java.util.List;

public interface AccountService {
    void createAccount(AccountRequest accountRequest);
    AccountResponse getAccountByActNo(String actNo);
    List<AccountResponse> getAllAccounts();
}

