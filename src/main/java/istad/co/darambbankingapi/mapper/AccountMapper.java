package istad.co.darambbankingapi.mapper;

import istad.co.darambbankingapi.domain.Account;
import istad.co.darambbankingapi.domain.User;
import istad.co.darambbankingapi.domain.UserAccount;
import istad.co.darambbankingapi.features.account.dto.AccountRequest;
import istad.co.darambbankingapi.features.account.dto.AccountResponse;
import istad.co.darambbankingapi.features.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "userAccountList", target = "user", qualifiedByName = "mapUserResponse")
    AccountResponse toAccountResponse(Account account);

    @Named(value = "mapUserResponse")
    default UserResponse mapUserResponse(List<UserAccount> userAccountList) {
        return toUserResponse(userAccountList.getFirst().getUser());
    }

    UserResponse toUserResponse(User user);

    Account fromAccountRequest(AccountRequest accountRequest);
}
