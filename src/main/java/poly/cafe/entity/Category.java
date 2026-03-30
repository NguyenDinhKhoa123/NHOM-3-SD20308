package poly.cafe.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean active = true;

    @OneToMany(mappedBy = "category")
    private List<Drink> drinks;
}