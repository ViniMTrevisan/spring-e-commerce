package trevisanvinicius.store.orders;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;
    private BigDecimal totalPrice;
}
