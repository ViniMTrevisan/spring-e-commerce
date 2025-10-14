package trevisanvinicius.store.products;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String s) {
        super(s);
    }
}
