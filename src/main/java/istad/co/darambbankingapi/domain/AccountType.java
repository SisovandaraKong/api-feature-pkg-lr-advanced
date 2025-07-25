package istad.co.darambbankingapi.domain;

import istad.co.darambbankingapi.enums.AccountTypeName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "account_types")
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean isDeleted;

    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;
}
