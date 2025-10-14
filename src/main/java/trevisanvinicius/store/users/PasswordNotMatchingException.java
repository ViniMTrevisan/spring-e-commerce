package trevisanvinicius.store.users;

public class PasswordNotMatchingException extends RuntimeException {
    public PasswordNotMatchingException(String s) {
        super(s);
    }
}
