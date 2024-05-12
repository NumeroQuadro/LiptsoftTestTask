package source.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import source.Models.Transaction;

import java.util.Collection;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Transaction findByTransactionUuid(UUID transactionUuid);
    @Query("SELECT t FROM Transaction t WHERE MONTH(t.date) = :month AND t.mcc = :mcc")
    Collection<Transaction> findByMonthWithProvidedMcc(int month, String mcc);
    @Query("SELECT t FROM Transaction t WHERE MONTH(t.date) = :month AND t.mcc = null")
    Collection<Transaction> findByMonthWithMccNull(int month);
}
