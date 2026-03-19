package poly.cafe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drinks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    private String image;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}