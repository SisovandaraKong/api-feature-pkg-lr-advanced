package istad.co.darambbankingapi.domain;

import istad.co.darambbankingapi.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
    private List<Authority> authorities;

    @Override
    public String getAuthority() {
        return "ROLE_" + name; // ROLE_ADMIN, ROLE_STAFF
    }
}
