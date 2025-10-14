package trevisanvinicius.store.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import trevisanvinicius.store.orders.PaymentStatus;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private PaymentStatus paymentStatus;
}
