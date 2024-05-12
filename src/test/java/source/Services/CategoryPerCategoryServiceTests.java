package source.Services;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import source.Models.Category;
import source.Models.CategoryPerCategory;
import source.Repositories.CategoryPerCategoryRepository;
import source.Repositories.CategoryRepository;
import source.Repositories.MccPerCategoryRepository;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CategoryPerCategoryServiceTests {
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private CategoryPerCategoryRepository categoryPerCategoryRepository;

    @Autowired
    @InjectMocks
    private CategoryPerCategoryService categoryPerCategoryService;

    @Test
    void testAddGroupToCategory_ThrowsIfParentCategoryDoesNotExist() {
        String parentCategoryName = "NonExistent";
        String childCategoryName = "Child";

        when(categoryRepository.findByName(parentCategoryName)).thenReturn(null);

        assertThrows(IllegalStateException.class,
                () -> categoryPerCategoryService.addGroupToCategory(parentCategoryName, childCategoryName),
                "Category \"NonExistent\" not found. Impossible to add category to non-existent category.");
        verify(categoryRepository).findByName(parentCategoryName);
        verifyNoInteractions(categoryPerCategoryRepository);
    }

    @Test
    void testAddGroupToCategory_ThrowsIfChildCategoryDoesNotExist() {
        String parentCategoryName = "Parent";
        String childCategoryName = "NonExistent";
        Category parentCategory = new Category();
        parentCategory.setId(1);
        parentCategory.setName(parentCategoryName);

        when(categoryRepository.findByName(parentCategoryName)).thenReturn(parentCategory);
        when(categoryRepository.findByName(childCategoryName)).thenReturn(null);

        assertThrows(IllegalStateException.class,
                () -> categoryPerCategoryService.addGroupToCategory(parentCategoryName, childCategoryName),
                "Category \"NonExistent\" not found. Impossible to add category to non-existent category.");
    }

    @Test
    @Transactional
    void testCycleDetection() {
        Category parent = new Category();
        parent.setId(1);
        Category child = new Category();
        child.setId(2);

        when(categoryRepository.findByName("Parent")).thenReturn(parent);
        when(categoryRepository.findByName("Child")).thenReturn(child);
        when(categoryPerCategoryRepository.findAllByParentCategoryId(anyInt()))
                .thenReturn(Collections.singletonList(new CategoryPerCategory(child, parent)));

        Exception exception = assertThrows(IllegalStateException.class, () ->
                categoryPerCategoryService.addGroupToCategory("Parent", "Child"));

        assertTrue(exception.getMessage().contains("Cycle detected"));
    }

    @Test
    @Transactional
    void testNestedCategoryExistence() {
        Category parent = new Category();
        parent.setId(1);
        Category child = new Category();
        child.setId(2);

        when(categoryRepository.findByName("Parent")).thenReturn(parent);
        when(categoryRepository.findByName("Child")).thenReturn(child);
        when(categoryPerCategoryRepository.findByParentCategoryAndChildCategory(parent, child))
                .thenReturn(new CategoryPerCategory(parent, child));

        Exception exception = assertThrows(IllegalStateException.class, () ->
                categoryPerCategoryService.addGroupToCategory("Parent", "Child"));

        assertTrue(exception.getMessage().contains("already reserved"));
    }
}
