package istad.co.darambbankingapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column(nullable = false, unique = true)
    private String nationalCardId;

    @Column(nullable = false)
    private Integer pin; //store 4 digit

    @Column(length = 10, nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String name;

    private String profileImage;

    @Column(length = 8, nullable = false)
    private String gender;

    private LocalDate dob; //dateOfBirth

    @Column(length = 100)
    private String cityOrProvince;

    @Column(length = 100)
    private String khanOrDistrict;

    @Column(length = 100)
    private String sangkatOrCommune;

    @Column(length = 100)
    private String village;

    @Column(length = 100)
    private String street;

    @Column(length = 100)
    private String employeeType;

    private String position;

    @Column(length = 100)
    private String companyName;

    @Column(length = 100)
    private String nameSourceOfIncome;

    @Column(length = 100)
    private BigDecimal monthlyIncomeRange;

    @Column(unique = true)
    private String oneSignalId;

    private Boolean isDeleted;
    private Boolean isStudent;
    private Boolean isBlocked;

    @Column(unique = true, nullable = false)
    private String studentCardId;

    @OneToMany(mappedBy = "user")
    private List<UserAccount> userAccountList;

    @ManyToMany(cascade = CascadeType.ALL)
    // This means when we fetch user,
    // it will fetch all roles that related to that user too
    @JoinTable(name = "users_roles"
    ,joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    ,inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    private LocalDateTime createdAt;
}
