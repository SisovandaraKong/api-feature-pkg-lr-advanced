package istad.co.darambbankingapi.features.accountType;

import istad.co.darambbankingapi.domain.AccountType;
import istad.co.darambbankingapi.features.accountType.dto.AccountTypeResponse;
import istad.co.darambbankingapi.mapper.AccountTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;

    @Override
    public List<AccountTypeResponse> getAllAccountTypes() {
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        return accountTypes
                .stream()
                .map(accountTypeMapper::toAccountTypeResponse)
                .toList();
    }

    @Override
    public AccountTypeResponse getAccountTypeByName(String name) {
        AccountType accountType = accountTypeRepository.findAccountTypeByName(name).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account type's name not found!"
        ));
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }
}
