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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category parentCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category childCategory;

    public CategoryPerCategory(Category parentCategory, Category childCategory) {
        this.parentCategory = parentCategory;
        this.childCategory = childCategory;
    }
}
