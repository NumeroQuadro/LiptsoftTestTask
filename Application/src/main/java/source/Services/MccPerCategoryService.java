package source.Services;

import jdk.dynalink.Operation;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import source.Models.Category;
import source.Models.MccPerCategory;
import source.Models.MccPerCategoryId;
import source.Repositories.CategoryPerCategoryRepository;
import source.Repositories.CategoryRepository;
import source.Repositories.MccPerCategoryRepository;
import source.ResultTypes.OperationResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MccPerCategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryPerCategoryRepository categoryPerCategoryRepository;
    @Autowired
    private MccPerCategoryRepository mccPerCategoryRepository;

    @Transactional
    public OperationResult addNewCategoryWithMcc(String categoryName, Collection<String> mccs) {
        Category category = categoryRepository.findByName(categoryName);

        if (category == null) {
            category = new Category();
            category.setName(categoryName);
            categoryRepository.save(category);
        }

        for (var mcc : mccs) {
            if (mccPerCategoryRepository.findByMcc(mcc) != null) {
                return new OperationResult.Failure("Mcc " + mcc + " already reserved for another category.");
            }

            MccPerCategory mccPerCategory = new MccPerCategory(category, mcc);
            mccPerCategoryRepository.save(mccPerCategory);
        }

        return new OperationResult.Success("Mccs " + mccs + " added to " + category.getName() + " category.");
    }

    @Transactional
    public void addNewMccToCategory(String categoryName, String mcc) {
        var category = getCategoryIfExists(categoryName);

        var categoryIdsContainsMccCode = getCategoryIdsWhichContainProvidedMccCode(mcc);
        if (!categoryIdsContainsMccCode.isEmpty()) {
            var categoryNames = categoryIdsContainsMccCode.stream()
                    .map(categoryRepository::findById)
                    .flatMap(Optional::stream)
                    .map(Category::getName)
                    .collect(Collectors.toList());

            throw new IllegalStateException("Mcc " + mcc + " already reserved for categories: " + String.join(", ", categoryNames));
        }

        var mccPerCategory = new MccPerCategory(category, mcc);
        mccPerCategoryRepository.save(mccPerCategory);
    }

    private Category getCategoryIfExists(String categoryName) {
        var category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new IllegalStateException("Category not found. Impossible to add mcc to non-existent category.");
        }

        return category;
    }

    private boolean mccIsReserved(String mcc) {
        var possibleMccPerCategory = mccPerCategoryRepository.findByMcc(mcc);

        return possibleMccPerCategory != null;
    }

    private Collection<Integer> getCategoryIdsWhichContainProvidedMccCode(String mcc) {
        return mccPerCategoryRepository.findDistinctCategoryIdsByMccCode(mcc);
    }
}
