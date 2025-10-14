package trevisanvinicius.store.payments;

import lombok.Data;

@Data
public class CheckoutResponseDTO {
    private Long orderId;
    private String checkoutUrl;

    public CheckoutResponseDTO(Long orderId, String checkoutUrl) {
        this.orderId = orderId;
        this.checkoutUrl = checkoutUrl;
    }
}
