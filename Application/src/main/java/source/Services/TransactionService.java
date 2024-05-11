package source.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import source.Models.Category;
import source.Models.Transaction;
import source.Repositories.CategoryPerCategoryRepository;
import source.Repositories.CategoryRepository;
import source.Repositories.MccPerCategoryRepository;
import source.Repositories.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryPerCategoryRepository categoryPerCategoryRepository;
    @Autowired
    private MccPerCategoryRepository mccPerCategoryRepository;

    public void addNewTransaction(BigDecimal amount, LocalDate date, String mcc) {
        var transactionUuid = UUID.randomUUID();
        var transaction = new Transaction(transactionUuid, amount, date, mcc);
        transactionRepository.save(transaction);
    }

    public void addNewTransactionWithoutMcc(BigDecimal amount, LocalDate date) {
        var transactionUuid = UUID.randomUUID();
        var transaction = new Transaction(transactionUuid, amount, date, null);
        transactionRepository.save(transaction);
    }

    public void removeTransaction(BigDecimal amount, LocalDate date, String mcc) {
        var transactionExample = Example.of(new Transaction(null, amount, date, mcc));
        var transactions = transactionRepository.findAll(transactionExample);

        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("Unable to delete transaction with date " + date + ", amount " + amount + " and mcc " + mcc + " which is not exists");
        }

        transactionRepository.delete(transactions.get(0));
    }

    public Map<String, BigDecimal> getTransactionsSumByCategoryInRequestedMonth(int month) {
        Map<String, BigDecimal> result = new HashMap<>();

        var categories = categoryRepository.findAll();
        for (var category : categories) {
            var sum = getSumOfAllTransactionsWithProvidedCategoryWithNestedChildrenPerMonth(category.getId(), month);
            result.put(category.getName(), sum);
        }

        return result;
    }

    private BigDecimal getSumOfAllTransactionsWithProvidedCategoryWithNestedChildrenPerMonth(Integer categoryId, int month) {
        var sum = BigDecimal.ZERO;

        var nestedCategories = categoryPerCategoryRepository.findAllByParentCategoryId(categoryId);


        for (var nestedCategory : nestedCategories) {
            sum = sum.add(getSumOfAllTransactionsWithProvidedCategoryWithNestedChildrenPerMonth(nestedCategory.getChildCategory().getId(), month));
        }

        sum = sum.add(getSumOfTransactionsWithProvidedCategory(categoryId, month));

        return sum;
    }

    private BigDecimal getSumOfTransactionsWithProvidedCategory(Integer categoryId, int month) {
        var sum = BigDecimal.ZERO;

        var mccs = mccPerCategoryRepository.findAllByCategoryId(categoryId);
        for (var mcc : mccs) {
            var transactions = transactionRepository.findByMonthWithProvidedMcc(month, mcc.getMcc());
            sum = transactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return sum;
    }
}
