package istad.co.darambbankingapi.init;

import istad.co.darambbankingapi.domain.Role;
import istad.co.darambbankingapi.enums.RoleName;
import istad.co.darambbankingapi.features.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleInit {
    private final RoleRepository roleRepository;

    @PostConstruct
    void init(){
    //Auto generate role (USER, STAFF, ADMIN, CUSTOMER)
        //Check if count have 0 record/data in database so inject
    if (roleRepository.count() < 1){
        Role role = new Role();
        role.setName(RoleName.USER);
        Role customer = new Role();
        customer.setName(RoleName.CUSTOMER);
        Role staff = new Role();
        staff.setName(RoleName.STAFF);
        Role admin = new Role();
        admin.setName(RoleName.ADMIN);
        roleRepository.saveAll(
                List.of(role, customer, staff, admin)
        );
    }
    }
}
