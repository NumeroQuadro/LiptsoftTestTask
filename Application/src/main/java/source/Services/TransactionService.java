package source.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.util.Pair;
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

    /**
     * Add new transaction to database
     * @param amount amount of transaction
     * @param date date of transaction
     * @param mcc mcc code of transaction
     */
    public void addNewTransaction(BigDecimal amount, LocalDate date, String mcc) {
        var transactionUuid = UUID.randomUUID();
        var transaction = new Transaction(transactionUuid, amount, date, mcc);
        transactionRepository.save(transaction);
    }

    /**
     * Add new transaction to database without mcc code
     * @param amount amount of transaction
     * @param date date of transaction
     */
    public void addNewTransactionWithoutMcc(BigDecimal amount, LocalDate date) {
        var transactionUuid = UUID.randomUUID();
        var transaction = new Transaction(transactionUuid, amount, date, null);
        transactionRepository.save(transaction);
    }

    /**
     * Remove transaction from database
     * @param amount amount of transaction
     * @param date date of transaction
     * @param mcc mcc code of transaction
     * @throws IllegalArgumentException if transaction does not exist
     */
    public void removeTransaction(BigDecimal amount, LocalDate date, String mcc) {
        var transactionExample = Example.of(new Transaction(null, amount, date, mcc));
        var transactions = transactionRepository.findAll(transactionExample);

        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("Unable to delete transaction with date " + date + ", amount " + amount + " and mcc " + mcc + " which is not exists");
        }

        transactionRepository.delete(transactions.get(0));
    }

    /**
     * Get sum of all transactions with provided category name
     * @return sum of all transactions with provided category name
     */
    public Map<String, Map<String, BigDecimal>> getTransactionsSumByCategoryInAllMonths() {
        Map<String, Map<String, BigDecimal>> result = new HashMap<>();

        var categories = categoryRepository.findAll();
        for (var category : categories) {
            var sumByMonth = new HashMap<String, BigDecimal>();
            for (int i = 1; i <= 12; i++) {
                var sum = getSumOfAllTransactionsWithProvidedCategoryWithNestedChildrenPerMonth(category.getId(), i);
                sumByMonth.put("Month number " + i, sum);
            }

            result.put(category.getName(), sumByMonth);
        }

        return result;
    }

    /**
     * Get sum of all transactions with provided category name in requested month
     * @param month month number
     * @return sum of all transactions with provided category name in requested month
     */
    public Map<String, BigDecimal> getTransactionsSumByCategoryInRequestedMonth(int month) {
        Map<String, BigDecimal> result = new HashMap<>();

        var categories = categoryRepository.findAll();
        for (var category : categories) {
            var sum = getSumOfAllTransactionsWithProvidedCategoryWithNestedChildrenPerMonth(category.getId(), month);
            result.put(category.getName(), sum);
        }

        result.put("Without category", getSumOfTransactionsWithProvidedCategoryAndMonth(null, month));

        return result;
    }

    /**
     * Get sum of all transactions with provided category name in requested month
     * @param categoryName category name
     * @param month month number
     * @return sum of all transactions with provided category name in requested month
     */
    public String getSumOfTransactionsWithProvidedCategoryNameAndMonth(String categoryName, int month) {
        var category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new IllegalArgumentException("Category with name " + categoryName + " not found");
        }

        var result = getSumOfTransactionsWithProvidedCategoryAndMonth(category.getId(), month);

        return "Sum of transactions with category " + categoryName + " in month number " + month + " is " + result;
    }

    private BigDecimal getSumOfAllTransactionsWithProvidedCategoryWithNestedChildrenPerMonth(Integer categoryId, int month) {
        var sum = BigDecimal.ZERO;

        var nestedCategories = categoryPerCategoryRepository.findAllByParentCategoryId(categoryId);


        for (var nestedCategory : nestedCategories) {
            sum = sum.add(getSumOfAllTransactionsWithProvidedCategoryWithNestedChildrenPerMonth(nestedCategory.getChildCategory().getId(), month));
        }

        sum = sum.add(getSumOfTransactionsWithProvidedCategoryAndMonth(categoryId, month));

        var transactionsWithoutMcc = transactionRepository.findByMonthWithMccNull(month);
        sum = sum.add(transactionsWithoutMcc.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));

        return sum;
    }

    private BigDecimal getSumOfTransactionsWithProvidedCategoryAndMonth(Integer categoryId, int month) {
        var sum = BigDecimal.ZERO;

        var mccs = mccPerCategoryRepository.findAllByCategoryId(categoryId);
        for (var mcc : mccs) {
            var transactions = transactionRepository.findByMonthWithProvidedMcc(month, mcc.getMcc());
            sum = transactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return sum;
    }
}
