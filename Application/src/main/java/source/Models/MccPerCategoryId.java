package source.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class MccPerCategoryId implements Serializable {
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "mcc", nullable = false)
    private String mccCode;

    public MccPerCategoryId(Integer categoryId, String mccCode) {
        this.categoryId = categoryId;
        this.mccCode = mccCode;
    }
}
