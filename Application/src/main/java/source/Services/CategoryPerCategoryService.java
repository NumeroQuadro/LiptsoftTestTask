package source.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import source.Models.Category;
import source.Models.CategoryPerCategory;
import source.Repositories.CategoryPerCategoryRepository;
import source.Repositories.CategoryRepository;
import source.Repositories.MccPerCategoryRepository;

import java.util.*;

@Service
public class CategoryPerCategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryPerCategoryRepository categoryPerCategoryRepository;
    @Autowired
    private MccPerCategoryRepository mccPerCategoryRepository;

    /**
     * Add group to category. If parent category is the same as child category, throw exception.
     * @param parentCategoryName name of parent category
     * @param childCategoryName name of child category
     * @throws IllegalStateException if parent category is the same as child category, if category is already reserved for another category, if cycle detected, if category already nested in any child category
     */
    @Transactional
    public void addGroupToCategory(String parentCategoryName, String childCategoryName) {
        if (parentCategoryName.equals(childCategoryName)) {
            throw new IllegalStateException("Category " + childCategoryName + " cannot be added to itself.");
        }

        var parentCategory = getCategoryIfExists(parentCategoryName);
        var childCategory = getCategoryIfExists(childCategoryName);
        if (isParentCategoryReserved(parentCategory, childCategory)) {
            throw new IllegalStateException("Category " + childCategoryName + " already reserved for another category.");
        }

        if (isThereChildCategoryWithProvidedId(childCategory.getId(), parentCategory.getId())) {
            throw new IllegalStateException("Cycle detected. Impossible to add category to itself.");
        }

        if (isThereChildCategoryWithProvidedId(parentCategory.getId(), childCategory.getId())) {
            throw new IllegalStateException("Category with name" + childCategoryName + " already nested in any child category. Impossible to add category to itself.");
        }

        var categoryPerCategory = new CategoryPerCategory(parentCategory, childCategory);
        categoryPerCategoryRepository.save(categoryPerCategory);
    }

    private Category getCategoryIfExists(String categoryName) {
        var category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new IllegalStateException("Category \"" + categoryName + "\" not found. Impossible to add category to non-existent category.");
        }

        return category;
    }

    private boolean isThereChildCategoryWithProvidedId(Integer currentParentCategoryId, Integer categoryIdToSearch) {
        var currentCategoryCollection = categoryPerCategoryRepository.findAllByParentCategoryId(currentParentCategoryId);

        for (var categoryPerCategory : currentCategoryCollection) {
            if (categoryPerCategory.getChildCategory().getId().equals(categoryIdToSearch)) {
                return true;
            }

            if (isThereChildCategoryWithProvidedId(categoryPerCategory.getChildCategory().getId(), categoryIdToSearch)) {
                return true;
            }
        }

        return false;
    }

    private boolean isParentCategoryReserved(Category parentCategory, Category childCategory) {
        return categoryPerCategoryRepository.findByParentCategoryAndChildCategory(parentCategory, childCategory) != null;
    }
}
