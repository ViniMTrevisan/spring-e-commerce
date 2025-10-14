package trevisanvinicius.store.carts;

public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException(){
        super("Cart was not found. Check cart Id");
    }
}
