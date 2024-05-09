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

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MccPerCategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryPerCategoryRepository categoryPerCategoryRepository;
    @Autowired
    private MccPerCategoryRepository mccPerCategoryRepository;

    @Transactional
    public OperationResult addNewCategoryWithMcc(String categoryName, String mcc) {
        var category = getCategoryIfExists(categoryName);

        if (mccIsReserved(mcc)) {
            throw new IllegalStateException("Mcc " + mcc + " already reserved for another category.");
        }

        var mccPerCategory = new MccPerCategory(category, mcc);
        mccPerCategoryRepository.save(mccPerCategory);

        return new OperationResult.Success("Mcc " + mcc + " added to " + category.getName() + " category.");
    }

    public void editCategoryWithNewMcc(String categoryName, String mcc) {
        var category = getCategoryIfExists(categoryName);

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
}
