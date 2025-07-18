package istad.co.darambbankingapi.domain;

import istad.co.darambbankingapi.enums.CardTypeName;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "card_types")
public class CardType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    @Enumerated(EnumType.STRING)
    private CardTypeName name;

    private Boolean isDeleted;

    @OneToMany(mappedBy = "cardType")
    @Enumerated(EnumType.STRING)
    private List<Card> cards;
}
