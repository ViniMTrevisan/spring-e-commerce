package trevisanvinicius.store.users;

public class EmailAlreadyExistsException extends Throwable {
    public EmailAlreadyExistsException(String s) {
        super(s);
    }
}
