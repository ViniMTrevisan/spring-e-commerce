package trevisanvinicius.store.products;

import jakarta.persistence.*;
import lombok.*;
import trevisanvinicius.store.orders.OrderItem;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<OrderItem> orderItem;
}