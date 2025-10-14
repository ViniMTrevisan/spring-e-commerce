package trevisanvinicius.store.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import trevisanvinicius.store.users.UserDto;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request,
            HttpServletResponse response
    ) {
        var authDto = authService.createLogin(request, response);
        return ResponseEntity.ok(authDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDTO> refresh(
            @CookieValue(value = "refresh_token") String refreshToken
    ) {
        var authDto = authService.createRefreshToken(refreshToken);
        return ResponseEntity.ok(authDto);
    }

    @GetMapping("/me")
    public UserDto me(){
        return authService.getMe();
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler({AuthenticationFailedException.class})
    public ResponseEntity<Void> handleAuthenticationFailedException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
