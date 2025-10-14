package trevisanvinicius.store.carts;

public class CartItemNotFoundException extends RuntimeException {

    public CartItemNotFoundException(){
        super("CartItem not found. Check if cart is not empty.");
    }
}
