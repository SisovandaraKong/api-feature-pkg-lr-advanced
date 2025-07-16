package istad.co.darambbankingapi.mapper;

import istad.co.darambbankingapi.domain.AccountType;
import istad.co.darambbankingapi.features.accountType.dto.AccountTypeRequest;
import istad.co.darambbankingapi.features.accountType.dto.AccountTypeResponse;
import istad.co.darambbankingapi.features.accountType.dto.UpdateAccountType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {
    AccountTypeResponse toAccountTypeResponse(AccountType accountType);
    AccountType fromAccountTypeRequest(AccountTypeRequest accountTypeRequest);
    void fromUpdateAccountType(UpdateAccountType updateAccountType, @MappingTarget AccountType accountType);
}
