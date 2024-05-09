package source.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import source.Models.Category;
import source.Models.CategoryPerCategory;

import java.util.Collection;

@Repository
public interface CategoryPerCategoryRepository extends JpaRepository<CategoryPerCategory, Integer> {
    CategoryPerCategory findByParentCategoryId(Integer categoryId);
    Collection<CategoryPerCategory> findAllByParentCategoryId(Integer categoryId);
    CategoryPerCategory findByParentCategoryAndChildCategory(Category parentCategory, Category childCategory);
}
