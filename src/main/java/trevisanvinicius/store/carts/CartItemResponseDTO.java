package trevisanvinicius.store.carts;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponseDTO {
    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;
    private BigDecimal totalPrice;
}