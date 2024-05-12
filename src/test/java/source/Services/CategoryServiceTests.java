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

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CategoryServiceTests {
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    @InjectMocks
    private CategoryService categoryService;

    @Test
    void addNewCategory_ThrowsIfCategoryExists() {
        String categoryName = "ExistingCategory";
        when(categoryRepository.findByName(categoryName)).thenReturn(new Category(categoryName));

        assertThrows(IllegalStateException.class, () -> categoryService.addNewCategory(categoryName),
                "Category already exists");

        verify(categoryRepository).findByName(categoryName);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void addNewCategory_SuccessfullyAddsCategory() {
        String categoryName = "NewCategory";
        when(categoryRepository.findByName(categoryName)).thenReturn(null);

        assertDoesNotThrow(() -> categoryService.addNewCategory(categoryName));
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void getAllCategories_ReturnsAllCategories() {
        List<Category> categories = List.of(new Category("Category1"), new Category("Category2"));
        when(categoryRepository.findAll()).thenReturn(categories);

        Collection<Category> retrievedCategories = categoryService.getAllCategories();

        assertNotNull(retrievedCategories);
        assertEquals(2, retrievedCategories.size());
        verify(categoryRepository).findAll();
    }

    @Test
    void removeCategory_ThrowsIfCategoryDoesNotExist() {
        String categoryName = "NonExistentCategory";
        when(categoryRepository.findByName(categoryName)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> categoryService.removeCategory(categoryName),
                "Unable to delete category with name \"NonExistentCategory\" which is not exists");
    }

    @Test
    void removeCategory_SuccessfullyRemovesCategory() {
        String categoryName = "ExistingCategory";
        Category category = new Category(categoryName);
        when(categoryRepository.findByName(categoryName)).thenReturn(category);

        assertDoesNotThrow(() -> categoryService.removeCategory(categoryName));
        verify(categoryRepository).delete(category);
    }

    @Test
    void getCategoryByName_ReturnsCategory() {
        String categoryName = "ExistingCategory";
        Category category = new Category(categoryName);
        when(categoryRepository.findByName(categoryName)).thenReturn(category);

        Category foundCategory = categoryService.getCategoryByName(categoryName);

        assertEquals(category, foundCategory);
        verify(categoryRepository).findByName(categoryName);
    }

}
