package istad.co.darambbankingapi.init;

import istad.co.darambbankingapi.domain.Authority;
import istad.co.darambbankingapi.domain.Role;
import istad.co.darambbankingapi.enums.RoleName;
import istad.co.darambbankingapi.features.user.AuthorityRepository;
import istad.co.darambbankingapi.features.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleInit {
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @PostConstruct
    void init() {
        //Auto generate role (USER, STAFF, ADMIN, CUSTOMER)
        //Check if count have 0 record/data in database so inject
        if (roleRepository.count() < 1) {

            Authority userRead = new Authority();
            userRead.setName("user:read");
            Authority userWrite = new Authority();
            userWrite.setName("user:write");
            Authority transactionRead = new Authority();
            transactionRead.setName("transaction:read");
            Authority transactionWrite = new Authority();
            transactionWrite.setName("transaction:write");
            Authority accountRead = new Authority();
            accountRead.setName("account:read");
            Authority accountWrite = new Authority();
            accountWrite.setName("account:write");
            Authority accountTypeRead = new Authority();
            accountTypeRead.setName("account:type:read");
            Authority accountTypeWrite = new Authority();
            accountTypeWrite.setName("account:type:write");

            Role user = new Role();
            user.setName(RoleName.USER);
            user.setAuthorities(List.of(
                    userRead,accountRead,accountTypeRead,transactionRead
            ));

            Role customer = new Role();
            customer.setName(RoleName.CUSTOMER);
            customer.setAuthorities(List.of(
                    userWrite,accountWrite,transactionWrite
            ));

            Role staff = new Role();
            staff.setName(RoleName.STAFF);
            staff.setAuthorities(List.of(
                    accountTypeWrite
            ));

            Role admin = new Role();
            admin.setName(RoleName.ADMIN);
            admin.setAuthorities(List.of(
                    userWrite,accountWrite,accountTypeWrite
            ));

            roleRepository.saveAll(
                    List.of(user, customer, staff, admin)
            );
        }

    }
}
