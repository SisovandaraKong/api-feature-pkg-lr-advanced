package istad.co.darambbankingapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(nullable = false)
    private String cvv;

    private String holder;

    private LocalDate issuedAt;

    private LocalDate expiredAt;

    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "card_type_id")
    private CardType cardType;

    @OneToOne(mappedBy = "card")
    private Account account;
}
