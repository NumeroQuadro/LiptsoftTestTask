package source.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class CategoryPerCategoryId implements Serializable {
    @Column(name = "parent_category_id")
    private Integer parentCategoryId;
    @Column(name = "child_category_id")
    private Integer childCategoryId;

    public CategoryPerCategoryId(Integer parentCategoryId, Integer childCategoryId) {
        this.parentCategoryId = parentCategoryId;
        this.childCategoryId = childCategoryId;
    }
}
