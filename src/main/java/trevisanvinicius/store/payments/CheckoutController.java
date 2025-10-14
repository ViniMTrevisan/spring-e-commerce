package trevisanvinicius.store.payments;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import trevisanvinicius.store.carts.CartItemNotFoundException;
import trevisanvinicius.store.carts.CartNotFoundException;
import trevisanvinicius.store.orders.OrderRepository;

import java.util.Map;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
@Setter
@Getter
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;

    @PostMapping
    @Transactional
    public CheckoutResponseDTO checkout(
            @Valid @RequestBody CheckoutRequestDTO request
    ) {
        return checkoutService.checkout(request);
    }

    @PostMapping("/webhook")
    public void handleWebHook(
            @RequestHeader Map<String, String> headers, // verify that the request actually came from stripe
            @RequestBody String payload // a json object that stripe sends us to tell us what happened.
            // we have to parse this into an event object
    ) {
        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }

    @ExceptionHandler({UsernameNotFoundException.class, CartNotFoundException.class, CartItemNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler({PaymentException.class})
    public ResponseEntity<ErrorDTO> handlePaymentException(Exception ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorDTO("Error creating a checkout session")
                );
    }

}
