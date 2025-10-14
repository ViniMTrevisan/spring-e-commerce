package trevisanvinicius.store.payments;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutRequestDTO {
    @NotNull(message = "cart id cannot be null")
    private UUID cartId;
}
