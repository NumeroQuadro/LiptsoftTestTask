package source.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mcc_per_category")
public class MccPerCategory {
    @EmbeddedId
    private MccPerCategoryId id;

    @MapsId("categoryId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "mcc", insertable = false, updatable = false)
    private String mcc;

    public MccPerCategory(Category category, String mcc) {
        this.category = category;
        this.mcc = mcc;
        this.id = new MccPerCategoryId(category.getId(), mcc);
    }
}
