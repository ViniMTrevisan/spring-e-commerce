package trevisanvinicius.store.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import trevisanvinicius.store.users.UserDto;
import trevisanvinicius.store.users.UserMapper;
import trevisanvinicius.store.users.UserRepository;
import trevisanvinicius.store.users.UserService;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;

    public Long checkAuthenticated() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new AuthenticationFailedException("User is not authenticated. Try logging in");
        }
        return (Long) auth.getPrincipal();
    }

    public JwtResponseDTO createLogin(LoginRequestDTO request, HttpServletResponse response) {
        System.out.println("trying to log in");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new BadCredentialsException("Invalid user")
        );

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var cookie = new Cookie("refresh_token", refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration()); // 7 days
        cookie.setSecure(true);
        response.addCookie(cookie);

        return new JwtResponseDTO(accessToken.toString());
    }


    public JwtResponseDTO createRefreshToken(String refreshToken) {
        var jwt = jwtService.parseToken(refreshToken);
        if (jwt == null || jwt.isExpired()) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        var user = userRepository.findById(jwt.getUserId()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);

        return new JwtResponseDTO(accessToken.toString());
    }

    public UserDto getMe() {
        var userId = checkAuthenticated();
        // the 2 lines above are about extracting the current principal (user)

        var user = userService.findUser(userId);
        // these lines are checking and validating

        return userMapper.toDto(user);
        // these lines are mapping and returning
    }

}
