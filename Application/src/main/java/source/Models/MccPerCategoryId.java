package source.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class MccPerCategoryId implements Serializable {
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "mcc")
    private String mcc;

    public MccPerCategoryId(Integer categoryId, String mcc) {
        this.categoryId = categoryId;
        this.mcc = mcc;
    }
}
