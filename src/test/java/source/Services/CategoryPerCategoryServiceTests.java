package source.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import source.Models.Category;
import source.Repositories.CategoryPerCategoryRepository;
import source.Repositories.CategoryRepository;
import source.Repositories.MccPerCategoryRepository;
import source.Services.CategoryPerCategoryService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CategoryPerCategoryServiceTests {
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private CategoryPerCategoryRepository categoryPerCategoryRepository;
    @MockBean
    private MccPerCategoryRepository mccPerCategoryRepository;

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

}
