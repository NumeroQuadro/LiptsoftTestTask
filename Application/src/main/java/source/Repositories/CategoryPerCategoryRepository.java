package source.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import source.Models.Category;
import source.Models.CategoryPerCategory;

import java.util.Collection;
import java.util.List;

@Repository
public interface CategoryPerCategoryRepository extends JpaRepository<CategoryPerCategory, Integer> {
    Collection<CategoryPerCategory> findAllByParentCategoryId(Integer categoryId);
    CategoryPerCategory findByParentCategoryAndChildCategory(Category parentCategory, Category childCategory);

}
