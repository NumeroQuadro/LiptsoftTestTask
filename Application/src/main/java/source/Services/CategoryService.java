package source.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import source.Models.Category;
import source.Models.MccPerCategory;
import source.Models.MccPerCategoryId;
import source.Repositories.CategoryPerCategoryRepository;
import source.Repositories.CategoryRepository;
import source.Repositories.MccPerCategoryRepository;

import java.math.BigDecimal;
import java.util.Collection;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryPerCategoryRepository categoryPerCategoryRepository;
    @Autowired
    private MccPerCategoryRepository mccPerCategoryRepository;

    /**
     * Add new category to database
     * @param categoryName category name to add
     * @throws IllegalStateException if category already exists
     */
    @Transactional
    public void addNewCategory(String categoryName) {
        var possibleCategory = getCategoryByName(categoryName);
        if (possibleCategory != null) {
            throw new IllegalStateException("Category already exists");
        }

        var category = new Category(categoryName);
        categoryRepository.save(category);
    }

    /**
     * Get category by name. Return null if not found
     * @param categoryName category name to get
     * @return category with provided name
     */
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    /**
     * Remove category by name
     * @param categoryName category name to remove
     * @throws IllegalArgumentException if category does not exist
     */
    public void removeCategory(String categoryName) {
        var category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new IllegalArgumentException("Unable to delete category with name \"" + categoryName + "\" which is not exists");
        }

        categoryRepository.delete(category);
    }

    /**
     * Get all categories
     * @return all categories
     */
    public Collection<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
