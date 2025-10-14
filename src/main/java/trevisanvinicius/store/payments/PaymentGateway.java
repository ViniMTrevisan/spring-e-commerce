package trevisanvinicius.store.payments;

import trevisanvinicius.store.common.CheckoutSession;
import trevisanvinicius.store.orders.Order;

import java.util.Optional;

public interface PaymentGateway {

    CheckoutSession createCheckoutSession(Order order);

    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
