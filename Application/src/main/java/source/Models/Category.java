package source.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Integer id;
    @Column(name="category_name", nullable = false, unique = true)
    private String name;
    @Column(name="category_description")
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
