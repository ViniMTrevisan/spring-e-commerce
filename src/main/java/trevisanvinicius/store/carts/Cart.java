package trevisanvinicius.store.carts;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id")
    private UUID id;

    @CreationTimestamp
    @Column(name = "dateCreated", nullable = false)
    private LocalDateTime dateCreated;

    @JsonManagedReference
    @OneToMany (
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<CartItem> cartItems = new ArrayList<>();

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;

        if (this.cartItems == null) {
            return totalPrice;
        }
        for (CartItem item : this.cartItems) {
            BigDecimal productPrice = item.getProduct().getPrice();
            int productQuantity = item.getQuantity();
            var subtotal = productPrice.multiply(BigDecimal.valueOf(productQuantity));
            totalPrice = totalPrice.add(subtotal);
        }
        return totalPrice;
    }

    public void removeItem(CartItem item) {
        this.cartItems.remove(item);
        item.setCart(null);
    }

    public void addItem(CartItem item) {
        this.cartItems.add(item);
    }

    public void removeItems(List<CartItem> items) {
        this.cartItems.removeAll(items);
    }


}
