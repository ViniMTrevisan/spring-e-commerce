package trevisanvinicius.store.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import trevisanvinicius.store.auth.AuthService;
import trevisanvinicius.store.carts.CartService;
import trevisanvinicius.store.orders.OrderRepository;
import trevisanvinicius.store.orders.OrderService;
import trevisanvinicius.store.users.UserService;

@Service
@RequiredArgsConstructor // only fields that are declared as final will be initialized
public class CheckoutService {
    private final AuthService authService;
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;


    public CheckoutResponseDTO checkout(CheckoutRequestDTO request) {
        var userId = authService.checkAuthenticated();
        if (userId == null){
            throw new UsernameNotFoundException("User not logged in");
        }
        var user = userService.findUser(userId);

        var cart = cartService.findCartByIdWithItems(request.getCartId());

        var savedOrder = orderService.createOrder(user, cart);

        // create a checkout session:
        try {
            var session = paymentGateway.createCheckoutSession(savedOrder);

            cartService.clearCart(cart.getId());

            return new CheckoutResponseDTO(savedOrder.getId(), session.getCheckoutUrl());
        } catch (PaymentException e) {
            orderRepository.delete(savedOrder); // we delete the order, because if client attempt multiple times,
            // we might create an order that has no meaning in our application
            throw e;
        }
    }

    public void handleWebhookEvent(WebhookRequest request) {
        paymentGateway
                .parseWebhookRequest(request)
                .ifPresent(payment -> {
                    var order = orderRepository.findById(payment.getOrderId()).orElseThrow();
                    order.setStatus(payment.getPaymentStatus());
                    orderRepository.save(order);
                });
    }

}
