package trevisanvinicius.store.carts;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import trevisanvinicius.store.products.Product;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",  nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public BigDecimal getTotalPrice() {
        return this.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
