package trevisanvinicius.store.users;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@AllArgsConstructor
@Getter
@Setter
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    //method: GET (retrieving Data), POST (creating data), PUT (updating data),
    // DELETE (deleting data)
    public Iterable<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ) {
        return userService.findAll(sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getSingleUser(id));
    }

    @PostMapping
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterUserDto request,
            UriComponentsBuilder uriBuilder) throws EmailAlreadyExistsException {

        var userDto = userService.registerNewUser(request);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") long Id,
            @RequestBody UpdateUserDto request) {
        var userDto = userService.updateSingleUser(Id, request);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteSingleUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public void changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {
        userService.changeUserPassword(id, request);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(
            UsernameNotFoundException ex
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error: ", ex.getMessage()));
    }

    @ExceptionHandler({EmailAlreadyExistsException.class})
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error: ", ex.getMessage()));
    }

    public ResponseEntity<Map<String, String>> handlePasswordNotMatching(PasswordNotMatchingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error: ", ex.getMessage()));
    }
}
