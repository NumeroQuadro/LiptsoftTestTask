package source.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import source.Models.Category;
import source.Models.MccPerCategory;
import source.Models.Transaction;
import source.Repositories.CategoryPerCategoryRepository;
import source.Repositories.CategoryRepository;
import source.Repositories.MccPerCategoryRepository;
import source.Repositories.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TransactionServiceTests {

    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private CategoryPerCategoryRepository categoryPerCategoryRepository;
    @MockBean
    private MccPerCategoryRepository mccPerCategoryRepository;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @Test
    void addNewTransaction_Success() {
        BigDecimal amount = new BigDecimal("100.00");
        LocalDate date = LocalDate.now();
        String mcc = "1234";
        Transaction expectedTransaction = new Transaction(UUID.randomUUID(), amount, date, mcc);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(expectedTransaction);

        assertDoesNotThrow(() -> transactionService.addNewTransaction(amount, date, mcc));

        verify(transactionRepository).save(any(Transaction.class));
    }


    @Test
    void removeTransaction_ThrowsWhenTransactionDoesNotExist() {
        BigDecimal amount = new BigDecimal("100.00");
        LocalDate date = LocalDate.now();
        String mcc = "1234";

        when(transactionRepository.findAll(any(Example.class))).thenReturn(Collections.emptyList());
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.removeTransaction(amount, date, mcc));
    }

    @Test
    void removeTransaction_SuccessfulDeletion() {
        BigDecimal amount = new BigDecimal("100.00");
        LocalDate date = LocalDate.now();
        String mcc = "1234";
        Transaction transaction = new Transaction(UUID.randomUUID(), amount, date, mcc);

        when(transactionRepository.findAll(any(Example.class))).thenReturn(List.of(transaction));
        doNothing().when(transactionRepository).delete(any(Transaction.class));
        assertDoesNotThrow(() -> transactionService.removeTransaction(amount, date, mcc));
        verify(transactionRepository).delete(transaction);
    }

    @Test
    void getTransactionsSumByCategoryInAllMonths_CalculatesCorrectly() {
        when(categoryRepository.findAll()).thenReturn(List.of(new Category("Food"), new Category("Utilities")));
        when(categoryPerCategoryRepository.findAllByParentCategoryId(any())).thenReturn(Collections.emptyList());
        when(mccPerCategoryRepository.findAllByCategoryId(any())).thenReturn(Collections.emptyList());
        when(transactionRepository.findByMonthWithProvidedMcc(anyInt(), anyString())).thenReturn(Collections.emptyList());

        Map<String, Map<String, BigDecimal>> results = transactionService.getTransactionsSumByCategoryInAllMonths();
        assertNotNull(results);
        assertTrue(results.containsKey("Food"));
        assertTrue(results.containsKey("Utilities"));
        assertEquals(12, results.get("Food").size());
        assertEquals(BigDecimal.ZERO, results.get("Food").get("Month number 1"));
    }

    @Test
    void testGetSumOfTransactionsForMonth() {
        Category electronics = new Category();
        electronics.setId(1);
        electronics.setName("Electronics");

        when(categoryRepository.findAll()).thenReturn(List.of(electronics));
        when(categoryPerCategoryRepository.findAllByParentCategoryId(1)).thenReturn(new ArrayList<>()); // No nested categories
        when(mccPerCategoryRepository.findAllByCategoryId(1)).thenReturn(List.of(new MccPerCategory(electronics, "1234")));

        List<Transaction> transactions = Arrays.asList(
                new Transaction(UUID.randomUUID(), new BigDecimal("100.00"), LocalDate.of(2021, 1, 10), "1234"),
                new Transaction(UUID.randomUUID(), new BigDecimal("150.00"), LocalDate.of(2021, 1, 20), "1234")
        );
        when(transactionRepository.findByMonthWithProvidedMcc(1, "1234")).thenReturn(transactions);

        Map<String, BigDecimal> sumResults = transactionService.getTransactionsSumByCategoryInRequestedMonth(1);
        BigDecimal sumForElectronics = sumResults.get("Electronics");

        assertNotNull(sumForElectronics);
        assertEquals(0, new BigDecimal("250.00").compareTo(sumForElectronics), "The sum should match the total of transactions for January.");
    }
}
