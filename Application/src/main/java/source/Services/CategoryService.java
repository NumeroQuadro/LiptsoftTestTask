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

    public void addNewCategory(String categoryName) {
        var possibleCategory = getCategoryByName(categoryName);
        if (possibleCategory != null) {
            throw new IllegalStateException("Category already exists");
        }

        var category = new Category(categoryName);
        categoryRepository.save(category);
    }

    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    public void removeCategory(String categoryName) {
        var category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new IllegalArgumentException("Unable to delete category with name \"" + categoryName + "\" which is not exists");
        }

        categoryRepository.delete(category);
    }

    public Collection<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
