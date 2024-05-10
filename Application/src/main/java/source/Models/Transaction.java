package source.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name="transaction_uuid", nullable = false, unique = true)
    private UUID transactionUuid;

    @Column(name="amount", nullable = false)
    private BigDecimal amount;

    @Column(name="date", nullable = false)
    private LocalDate date;

    @Column(name="mcc")
    private String mcc;


    public Transaction(UUID transactionUuid, BigDecimal amount, LocalDate date, String mcc) {
        this.transactionUuid = transactionUuid;
        this.amount = amount;
        this.date = date;
        this.mcc = mcc;
    }
}
