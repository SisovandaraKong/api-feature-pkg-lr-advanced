package istad.co.darambbankingapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 9)
    private String actNo;

    @Column(unique = true, length = 100)
    private String actName;

    @Column(length = 100)
    private String alies; //nickname

    @Column(nullable = false)
    private BigDecimal balance;

    private BigDecimal transferLimit;

    // Any accounts have a type
    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;

    // Any Accounts have a user
    @OneToMany(mappedBy = "account")
    private List<UserAccount> userAccountList;

    @OneToOne
    private Card card;

    private Boolean isHidden;
}
