package source.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import source.Models.MccPerCategory;
import source.Models.MccPerCategoryId;

import java.util.Collection;
import java.util.List;

@Repository
public interface MccPerCategoryRepository extends JpaRepository<MccPerCategory, Integer> {
    MccPerCategory findById(MccPerCategoryId id);
    boolean existsById(MccPerCategoryId id);
    MccPerCategory findByMcc(String mcc);
    MccPerCategory findByCategoryId(Integer categoryId);
    Collection<MccPerCategory> findAllByCategoryId(Integer categoryId);
    @Query("SELECT DISTINCT c.id FROM MccPerCategory c WHERE c.mcc = :mcc")
    List<Integer> findDistinctCategoryIdsByMccCode(@Param("mcc") String mcc);
}
