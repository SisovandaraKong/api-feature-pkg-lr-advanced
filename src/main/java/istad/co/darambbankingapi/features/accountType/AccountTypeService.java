package istad.co.darambbankingapi.features.accountType;

import istad.co.darambbankingapi.features.accountType.dto.AccountTypeRequest;
import istad.co.darambbankingapi.features.accountType.dto.AccountTypeResponse;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> getAllAccountTypes();
    AccountTypeResponse getAccountTypeByName(String name);
}
