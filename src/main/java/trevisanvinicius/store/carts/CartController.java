package trevisanvinicius.store.carts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import trevisanvinicius.store.products.ProductRequestDTO;
import trevisanvinicius.store.orders.InvalidQuantityException;
import trevisanvinicius.store.products.ProductNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@Controller
@Getter
@Setter
@AllArgsConstructor
@RequestMapping("/carts")
@Tag(name = "carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> registerCart(
            UriComponentsBuilder uriBuilder
    ) {
        var cartResponseDto = cartService.createCart();

        var uri =  uriBuilder.path("/carts/{id}").buildAndExpand(cartResponseDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartResponseDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "adds a product to the cart")
    public ResponseEntity<?> addProducttoCart(
            @PathVariable UUID cartId,
            @Valid @RequestBody ProductRequestDTO request
            ) {

        var cartItemDto = cartService.addToCart(cartId, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCart(@PathVariable UUID cartId) {
        return ResponseEntity.ok(cartService.getSingleCart(cartId));
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
            @RequestBody CartItemUpdateRequestDTO request) {

        var updatedCart = cartService.updateCart(cartId, productId, request);

        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    @Transactional
    public ResponseEntity<?> deleteCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId) {

        cartService.deleteCartItem(cartId, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    @Transactional
    public ResponseEntity<?> deleteCartItems(
            @PathVariable UUID cartId
    )
    {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({CartNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleCartNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Cart not found"));
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error", "Product not found in the cart"));
    }

    @ExceptionHandler({CartItemNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleCartItemNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Cart item not found in the cart"));
    }

    @ExceptionHandler({InvalidQuantityException.class})
    public ResponseEntity<Map<String, String>> handleInvalidQuantityException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error", "Invalid quantity. Must be greater than 0 and less than 100"));
    }
}
