package source.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import source.Models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
