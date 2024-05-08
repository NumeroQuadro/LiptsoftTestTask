package source.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import source.Models.MccPerCategory;

@Repository
public interface MccPerCategoryRepository extends JpaRepository<MccPerCategory, Integer> {
}
