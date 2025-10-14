package trevisanvinicius.store.carts;

import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CartResponseDTO {
    private UUID id;
    private List<CartItemResponseDTO> items = new ArrayList<>();
    private BigDecimal totalPrice;
}