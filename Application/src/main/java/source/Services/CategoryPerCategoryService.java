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

    @Transactional
    public void addGroupToCategory(String parentCategoryName, String childCategoryName) {
        // todo: check if parent and child categories are same
        var parentCategory = getCategoryIfExists(parentCategoryName);
        var childCategory = getCategoryIfExists(childCategoryName);
        if (isParentCategoryReserved(parentCategory, childCategory)) {
            throw new IllegalStateException("Category " + childCategoryName + " already reserved for another category.");
        }

        if (isThereCycleWithNewLink(parentCategory.getId(), childCategory.getId())) {
            throw new IllegalStateException("Cycle detected. Impossible to add category to itself.");
        }

        if (isThereNestedCategoryWithSameChildId(parentCategory, childCategory)) {
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

    private boolean isThereCycleWithNewLink(Integer parentCategoryId, Integer possibleChildCategoryId) {
        var allCategoriesById = categoryPerCategoryRepository.findAllByParentCategoryId(parentCategoryId);
        if (allCategoriesById.isEmpty()) {
            return false;
        }
        for (var categoryPerCategory : allCategoriesById) {
            if (categoryPerCategory.getChildCategory().getId().equals(possibleChildCategoryId)) {
                return true;
            }
            if (isThereCycleWithNewLink(categoryPerCategory.getChildCategory().getId(), possibleChildCategoryId)) {
                return true;
            }
        }

        return false;
    }

    private boolean isThereNestedCategoryWithSameChildId(Category parentCategory, Category possibleChildCategory) {
        // add all tree items to map and then check if set power equals to list power
        Set<Category> set = new HashSet<>();
        var updatedSet = recursiveAddCategoriesToMap(parentCategory.getId(), set);
        var updatedSetSize = updatedSet.size();

        set.add(possibleChildCategory);
        return set.size() == updatedSetSize;
    }

    private Set<Category> recursiveAddCategoriesToMap(Integer parentCategoryId, Set<Category> set) {
        var allCategoriesById = categoryPerCategoryRepository.findAllByParentCategoryId(parentCategoryId);
        for (var categoryPerCategory : allCategoriesById) {
            set.add(categoryPerCategory.getChildCategory());
            return recursiveAddCategoriesToMap(categoryPerCategory.getChildCategory().getId(), set);
        }

        return set;
    }

    private boolean isParentCategoryReserved(Category parentCategory, Category childCategory) {
        return categoryPerCategoryRepository.findByParentCategoryAndChildCategory(parentCategory, childCategory) != null;
    }
}
