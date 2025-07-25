package istad.co.darambbankingapi.features.account.dto;

import istad.co.darambbankingapi.features.accountType.dto.AccountTypeResponse;
import istad.co.darambbankingapi.features.user.dto.UserResponse;

import java.math.BigDecimal;

public record AccountResponse(
        String actName,
        String alias,
        String actNo,
        BigDecimal balance,
        BigDecimal transferLimit,
        AccountTypeResponse accountType,
        UserResponse user
) {
}
