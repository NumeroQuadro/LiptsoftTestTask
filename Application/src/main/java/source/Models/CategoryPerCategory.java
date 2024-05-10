package source.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "category_per_category")
public class CategoryPerCategory {
    @EmbeddedId
    private CategoryPerCategoryId id;

    @MapsId("parentCategoryId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id", nullable = false)
    private Category parentCategory;

    @MapsId("childCategoryId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child_category_id", nullable = false)
    private Category childCategory;

    public CategoryPerCategory(Category parentCategory, Category childCategory) {
        this.parentCategory = parentCategory;
        this.childCategory = childCategory;
        this.id = new CategoryPerCategoryId(parentCategory.getId(), childCategory.getId());
    }
}
