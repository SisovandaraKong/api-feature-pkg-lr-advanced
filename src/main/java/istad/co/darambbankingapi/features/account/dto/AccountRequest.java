package istad.co.darambbankingapi.features.account.dto;

import istad.co.darambbankingapi.enums.AccountTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountRequest(
        @NotBlank(message = "Alias is required")
        String alias,

        @NotNull(message = "First balance is required")
        BigDecimal balance,

        @NotBlank(message = "Account Type is required")
        String accountTypeName,

        @NotBlank(message = "User is required")
        String userUuid,

        String cardNumber // If user create account type card
) {
}
