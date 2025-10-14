package trevisanvinicius.store.carts;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import trevisanvinicius.store.orders.InvalidQuantityException;
import trevisanvinicius.store.products.Product;
import trevisanvinicius.store.products.ProductMapper;
import trevisanvinicius.store.products.ProductService;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private CartItemRepository cartItemRepository;

    public Cart findCartByIdWithItems(UUID cartId) {
        return cartRepository.findByIdWithItems(cartId)
                .orElseThrow(CartNotFoundException::new);
    }

    public CartItem findCartItemByCartAndProduct(
            Cart cart,
            Product product) {

        var cartItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);
        if (cartItem == null) {
            throw new CartItemNotFoundException();
        }

        if (cartItem.getCart().getCartItems().isEmpty()) {
            throw new CartItemNotFoundException();
        }

        return cartItem;
    }

    public CartResponseDTO createCart() {
        var cart = new Cart();
        var savedCart = cartRepository.save(cart);

        return cartMapper.toDto(savedCart);
    }

    @Transactional
    public CartItemResponseDTO addToCart(UUID cartId, Long productId) {
        CartItem cartItem;

        var cart = findCartByIdWithItems(cartId);

        var product = productService.findProductEntityById(productId);

        Optional<CartItem> cartExists = cartItemRepository.findByCartAndProduct(cart, product);

        if (cartExists.isPresent()) {
            cartItem = cartExists.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            CartItem newCartItem = new CartItem();
            var quantity = 1;
            newCartItem.setProduct(product);
            newCartItem.setCart(cart);
            newCartItem.setQuantity(quantity);
            cartItem = newCartItem;
        }

        var savedCartItem = cartItemRepository.save(cartItem);

        return cartMapper.toDto(savedCartItem);
    }

    public CartResponseDTO getSingleCart(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        return cartMapper.toDto(cart);
    }

    public CartItemResponseDTO updateCart(
            UUID cartId,
            Long productId,
            CartItemUpdateRequestDTO request) {

        var cart = findCartByIdWithItems(cartId);

        var product = productService.findProductEntityById(productId);

        var cartItem = findCartItemByCartAndProduct(cart, product);

        if (request.getQuantity() >= 1 && request.getQuantity() <= 100) {
            cartItem.setQuantity(request.getQuantity());
            cartItemRepository.save(cartItem);
            return cartMapper.toDto(cartItem);
        } else {
            throw new InvalidQuantityException();
        }
    }

    public void deleteCartItem(
            UUID cartId,
            Long productId) {

        var cart = findCartByIdWithItems(cartId);

        var product = productService.findProductEntityById(productId);

        var cartItem = findCartItemByCartAndProduct(cart, product);

        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);
    }

    public void clearCart(UUID cartId) {

        var cart = findCartByIdWithItems(cartId);

        if (cart.getCartItems().isEmpty()) {
            throw new CartItemNotFoundException();
        }

        cart.removeItems(cartItemRepository.deleteAllByCart(cart));

    }

}
