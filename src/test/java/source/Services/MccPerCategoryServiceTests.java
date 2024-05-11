package source.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import source.Models.Category;
import source.Models.MccPerCategory;
import source.Repositories.CategoryPerCategoryRepository;
import source.Repositories.CategoryRepository;
import source.Repositories.MccPerCategoryRepository;
import source.ResultTypes.OperationResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MccPerCategoryServiceTests {
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private CategoryPerCategoryRepository categoryPerCategoryRepository;
    @MockBean
    private MccPerCategoryRepository mccPerCategoryRepository;

    @Autowired
    @InjectMocks
    private MccPerCategoryService mccPerCategoryService;

    @Test
    void testAddNewCategoryWithMcc_Success() {
        String categoryName = "Electronics";
        String mcc = "1234";
        Category category = new Category();
        category.setName(categoryName);

        when(categoryRepository.findByName(categoryName)).thenReturn(category);
        when(mccPerCategoryRepository.findByMcc(mcc)).thenReturn(null);

        OperationResult result = mccPerCategoryService.addNewCategoryWithMcc(categoryName, mcc);

        assertInstanceOf(OperationResult.Success.class, result);
        assertEquals("Mcc 1234 added to Electronics category.", ((OperationResult.Success) result).getSuccessMessage());
        verify(mccPerCategoryRepository).save(any(MccPerCategory.class));
    }

    @Test
    void testAddNewCategoryWithMcc_MccReserved() {
        String categoryName = "Electronics";
        String mcc = "1234";
        Category category = new Category();
        category.setName(categoryName);

        when(categoryRepository.findByName(categoryName)).thenReturn(category);
        when(mccPerCategoryRepository.findByMcc(mcc)).thenReturn(new MccPerCategory());

        assertThrows(IllegalStateException.class, () -> mccPerCategoryService.addNewCategoryWithMcc(categoryName, mcc));
    }

    @Test
    void testAddNewMccToCategory_Success() {
        String categoryName = "Electronics";
        String mcc = "5678";
        Category category = new Category();
        category.setName(categoryName);

        when(categoryRepository.findByName(categoryName)).thenReturn(category);
        when(mccPerCategoryRepository.findDistinctCategoryIdsByMccCode(mcc)).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> mccPerCategoryService.addNewMccToCategory(categoryName, mcc));
        verify(mccPerCategoryRepository).save(any(MccPerCategory.class));
    }

    @Test
    void testAddNewMccToCategory_MccAlreadyReserved() {
        String categoryName = "Electronics";
        String mcc = "5678";
        Category category = new Category();
        category.setName(categoryName);
        List<Integer> categoryIds = Arrays.asList(1, 2);  // Assume these categories already use the MCC

        when(categoryRepository.findByName(categoryName)).thenReturn(category);
        when(mccPerCategoryRepository.findDistinctCategoryIdsByMccCode(mcc)).thenReturn(categoryIds);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category("Other")));
        when(categoryRepository.findById(2)).thenReturn(Optional.of(new Category("Another")));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> mccPerCategoryService.addNewMccToCategory(categoryName, mcc));
        assertTrue(exception.getMessage().contains("already reserved for categories: Other, Another"));
    }


}
