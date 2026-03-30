package poly.cafe.entity;

import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "drinks")
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Đồng bộ với DECIMAL(10,2) trong SQL
    @Column(name = "price") // Chỉ cần thế này là đủ
    private Double price;

    private String image;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}