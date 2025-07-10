package istad.co.darambbankingapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account owner;

    @ManyToOne
    @JoinColumn(name = "transfer_receiver_id")
    private Account transferReceiver;

    private String paymentReceiver;

    private BigDecimal amount;

    private String remark;

    @Column(nullable = false,length = 100)
    private String transactionType;

    private Boolean status;

    private Boolean isPayment;

    private LocalDateTime transactionAt;

}
