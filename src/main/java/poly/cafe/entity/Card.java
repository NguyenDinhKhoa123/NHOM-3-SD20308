package poly.cafe.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_code", unique = true)
    private String cardCode;

    private String status;
    private Boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}