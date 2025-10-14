package trevisanvinicius.store.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private long id;
    private String name;
    private String email;
}
