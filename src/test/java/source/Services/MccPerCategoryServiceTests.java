package source.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import source.Models.Category;
import source.Models.MccPerCategory;
import source.Repositories.CategoryPerCategoryRepository;
import source.Repositories.CategoryRepository;
import source.Repositories.MccPerCategoryRepository;

import java.util.*;

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
    private MccPerCategoryRepository mccPerCategoryRepository;

    @Autowired
    @InjectMocks
    private MccPerCategoryService mccPerCategoryService;

    @Test
    @Transactional
    void testAddNewCategoryWithMcc_Success() {
        var category = new Category();
        String categoryName = "Electronics";
        category.setId(1);
        category.setName(categoryName);
        List<String> mccs = Collections.singletonList("1234");

        when(categoryRepository.findByName(categoryName)).thenReturn(category);
        when(mccPerCategoryRepository.findByMcc("1234")).thenReturn(null);

        assertDoesNotThrow(() -> mccPerCategoryService.addNewCategoryWithMcc(categoryName, mccs));
        verify(mccPerCategoryRepository).save(any(MccPerCategory.class));
    }

    @Test
    void testAddNewCategoryWithMcc_CategoryNotExist_CreateNewCategoryAndSuccess() {
        String categoryName = "Electronics";
        List<String> mccs = Collections.singletonList("1234");

        when(categoryRepository.findByName(categoryName)).thenReturn(null);
        when(mccPerCategoryRepository.findByMcc("1234")).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            category.setId(1);
            return category;
        });

        assertDoesNotThrow(() -> mccPerCategoryService.addNewCategoryWithMcc(categoryName, mccs));
        verify(categoryRepository).save(any(Category.class));
        verify(mccPerCategoryRepository).save(any(MccPerCategory.class));
    }

    @Test
    void testAddNewCategoryWithMcc_MccReserved() {
        String categoryName = "Electronics";
        List<String> mccs = Arrays.asList("1234", "5678");
        Category category = new Category();
        category.setName(categoryName);

        when(categoryRepository.findByName(categoryName)).thenReturn(category);
        when(mccPerCategoryRepository.findByMcc("1234")).thenReturn(new MccPerCategory());

        assertThrows(IllegalStateException.class, () -> mccPerCategoryService.addNewCategoryWithMcc(categoryName, mccs));
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
        List<Integer> categoryIds = Arrays.asList(1, 2);

        when(categoryRepository.findByName(categoryName)).thenReturn(category);
        when(mccPerCategoryRepository.findDistinctCategoryIdsByMccCode(mcc)).thenReturn(categoryIds);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category("Other")));
        when(categoryRepository.findById(2)).thenReturn(Optional.of(new Category("Another")));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> mccPerCategoryService.addNewMccToCategory(categoryName, mcc));
        assertTrue(exception.getMessage().contains("already reserved for categories: Other, Another"));
    }


}
