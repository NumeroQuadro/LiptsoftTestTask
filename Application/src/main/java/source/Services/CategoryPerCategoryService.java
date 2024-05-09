package source.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import source.Models.Category;
import source.Models.CategoryPerCategory;
import source.Models.MccPerCategory;
import source.Repositories.CategoryPerCategoryRepository;
import source.Repositories.CategoryRepository;
import source.Repositories.MccPerCategoryRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryPerCategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryPerCategoryRepository categoryPerCategoryRepository;
    @Autowired
    private MccPerCategoryRepository mccPerCategoryRepository;

    /**
     * Add new category with parent category name and child category name.
     * Checks if parent category exists,if child category exists,
     * if child category is reserved for another category and
     * if there is a cycle in the category tree.
     * @param parentCategoryName parent category name
     * @param childCategoryName child category name
     */
    @Transactional
    public void addNewCategoryWithCategory(String parentCategoryName, String childCategoryName) {
        var parentCategory = getCategoryIfExists(parentCategoryName);
        var childCategory = getCategoryIfExists(parentCategoryName);
        if (isParentCategoryReserved(parentCategory, childCategory)) {
            throw new IllegalStateException("Category " + childCategoryName + " already reserved for another category.");
        }

        if (isThereCycleWithNewLink(parentCategory.getId(), childCategory.getId())) {
            throw new IllegalStateException("Cycle detected. Impossible to add category to itself.");
        }

        if (isThereNestedCategoryWithSameChildId(parentCategory, childCategory)) {
            throw new IllegalStateException("Category with name" + childCategoryName + " already nested in any child category. Impossible to add category to itself.");
        }

        // todo: check if child of current parent category has same mccs as new category
        // todo: chekc if parent categories of current category have same mccs as new category
        var collectionOfCategoriesWithSameMccsAsGiveOne = getCategoriesWhichHaveSameMccsAsGivenOne(parentCategory.getId());
        if (!collectionOfCategoriesWithSameMccsAsGiveOne.isEmpty()) {
            var collectionNamesOfCategoriesWithSameMccsAsGivenOne = categoryRepository.findAllById(collectionOfCategoriesWithSameMccsAsGiveOne)
                    .stream()
                    .map(Category::getName)
                    .toList();

            throw new IllegalStateException("Mcc already reserved for categories in this list:" + collectionNamesOfCategoriesWithSameMccsAsGivenOne);
        }

        var mccsRelatedToParentCategory = getMccsByCategoryId(parentCategory.getId());
        if (mccsRelatedToParentCategory.contains())

        var categoryPerCategory = new CategoryPerCategory(parentCategory, childCategory);
        categoryPerCategoryRepository.save(categoryPerCategory);
    }

    /**
     * Check if category exists in all categories table. If not, throw IllegalStateException.
     * @param categoryName name category to extract
     * @throws IllegalStateException if category not found
     * @return Category
     */
    private Category getCategoryIfExists(String categoryName) {
        var category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new IllegalStateException("Category" + categoryName + " not found. Impossible to add category to non-existent category.");
        }

        return category;
    }

    /**
     * Get categories which have same mccs as given one by calling
     * `findDistinctCategoryIdsByMccCodes` query-based method in appropriate repository.
     * @param parentCategoryId parent category id
     * @return Collection<Integer> collection of category ids
     */
    // category a = 1111 2222
    // category b = 3333 4444
    // category c = 5555 3333
    // category a --> category b
    // category a --> category c ???
    private Collection<Integer> getCategoriesWhichHaveSameMccsAsGivenOneInMccPerCategoriesTable(Integer parentCategoryId) {
        var mccsCollection = mccPerCategoryRepository.findAllByCategoryId(parentCategoryId)
                .stream()
                .filter(Objects::nonNull)
                .map(MccPerCategory::getMcc)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // category a
        // category b
        // category a --> category b
        // category b --> category c
        // category c + mcc=2323
        // category a + mcc=2323 ???

        // category a
        // category b
        // category a --> category b
        // category b --> category c
        // category a + mcc=2323
        // category c + mcc=2323 ???

        return mccPerCategoryRepository.findDistinctCategoryIdsByMccCodes(mccsCollection);
    }

    private Collection<Integer> getCategoriesWhichHaveSameMccsAsGivenOneInCategoryPerCategoryTable(Integer parentCategoryId) {
        var allCategoriesById = categoryPerCategoryRepository.findAllByParentCategoryId(parentCategoryId).stream().map(CategoryPerCategory::getChildCategory).toList();
        if (allCategoriesById.isEmpty()) {
            return Collections.emptyList();
        }

        var mccsCollection =

        return mccPerCategoryRepository.findDistinctCategoryIdsByMccCodes(mccsCollection);
    }

    private Collection<String> getMccsByCategoryId(Integer categoryId) {
        return mccPerCategoryRepository.findAllByCategoryId(categoryId)
                .stream()
                .map(MccPerCategory::getMcc)
                .toList();
    }

    /**
     * Check recursive if there is a cycle in the category tree with the new link.
     * Start with parent category id and compare all child category ids with
     * new possible child category id
     * @param parentCategoryId parent category id
     * @param possibleChildCategoryId possible child category id
     * @return boolean
     */
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

    // 1 2
    // 2 3
    // 3 1

    /**
     * Check if there are several same children in the parent category.
     * If after adding possible child category to set,
     * set size is equal to old set size --> there will be same category
     * ids in the category tree --> there are nested categories
     * @param parentCategory parent category
     * @param possibleChildCategory possible child category
     * @return boolean
     */
    private boolean isThereNestedCategoryWithSameChildId(Category parentCategory, Category possibleChildCategory) {
        // add all tree items to map and then check if set power equals to list power
        Set<Category> set = new HashSet<>();
        var updatedSet = recursiveAddCategoriesToMap(parentCategory.getId(), set);
        var updatedSetSize = updatedSet.size();

        set.add(possibleChildCategory);
        return set.size() == updatedSetSize;
    }

    /**
     * Recursive add all categories to map.
     * @param parentCategoryId parent category id
     * @param set set of categories to be future filled
     * @return Set<Category> filled set of categories
     */
    private Set<Category> recursiveAddCategoriesToMap(Integer parentCategoryId, Set<Category> set) {
        var allCategoriesById = categoryPerCategoryRepository.findAllByParentCategoryId(parentCategoryId);
        for (var categoryPerCategory : allCategoriesById) {
            set.add(categoryPerCategory.getChildCategory());
            return recursiveAddCategoriesToMap(categoryPerCategory.getChildCategory().getId(), set);
        }

        return set;
    }
    // 1 2
    // 2 3
    // 1 3

    /**
     * Get row with by primary key with parent and child category. If exists, return true.
     * @param parentCategory parent category
     * @param childCategory child category
     * @return boolean
     */
    private boolean isParentCategoryReserved(Category parentCategory, Category childCategory) {
        return categoryPerCategoryRepository.findByParentCategoryAndChildCategory(parentCategory, childCategory) != null;
    }
}
