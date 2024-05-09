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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "mcc", nullable = false)
    private String mcc;

    public MccPerCategory(Category category, String mcc) {
        this.category = category;
        this.mcc = mcc;
    }
}
