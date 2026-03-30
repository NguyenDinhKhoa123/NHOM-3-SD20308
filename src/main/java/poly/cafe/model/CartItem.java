package poly.cafe.model; // Tạo package model mới

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CartItem {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private String image;
}