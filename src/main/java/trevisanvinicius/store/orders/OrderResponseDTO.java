package trevisanvinicius.store.orders;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items =  new ArrayList<>();
    private BigDecimal totalPrice;
}
