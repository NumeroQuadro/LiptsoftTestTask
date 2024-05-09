package source.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import source.Models.MccPerCategory;
import source.Models.MccPerCategoryId;

@Repository
public interface MccPerCategoryRepository extends JpaRepository<MccPerCategory, Integer> {
    MccPerCategory findById(MccPerCategoryId id);
    boolean existsById(MccPerCategoryId id);
    MccPerCategory findByMcc(String mcc);
}
