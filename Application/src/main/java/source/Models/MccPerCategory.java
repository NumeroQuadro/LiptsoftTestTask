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
    @Id
    @OneToOne(mappedBy = "category_id", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Category category;
    private Integer mcc;

    public MccPerCategory(Category category, Integer mcc) {
        this.category = category;
        this.mcc = mcc;
    }
}
