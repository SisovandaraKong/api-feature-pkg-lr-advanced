package istad.co.darambbankingapi.init;

import istad.co.darambbankingapi.domain.AccountType;
import istad.co.darambbankingapi.enums.AccountTypeName;
import istad.co.darambbankingapi.features.accountType.AccountTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountTypeInit {
    private final AccountTypeRepository accountTypeRepository;

    @PostConstruct
    void initAccountTypeData() {
    if (accountTypeRepository.count() < 1) {
        AccountType payroll = new AccountType();
        payroll.setName(AccountTypeName.PAYROLL);
        payroll.setDescription("Payroll is an account which use everyday to pay for things");
        payroll.setIsDeleted(false);
        AccountType saving = new AccountType();
        saving.setName(AccountTypeName.SAVING);
        saving.setDescription("Saving is an account which use to save money that we don't wanna pay");
        saving.setIsDeleted(false);
        AccountType card = new AccountType();
        card.setName(AccountTypeName.CARD);
        card.setDescription("Card is an account which use to pay for online payment");
        card.setIsDeleted(false);
        accountTypeRepository.saveAll(List.of(payroll, saving, card));
    }
    }
}
