package trevisanvinicius.store.users;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import trevisanvinicius.store.auth.PasswordConfig;

import java.util.*;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordConfig passwordConfig;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return new User(
                user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

    public List<UserDto> findAll(String sort) {
        if (!Set.of("name", "email").contains(sort)) {
            sort = "name";
        }
        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getSingleUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    public UserDto registerNewUser(RegisterUserDto request) throws EmailAlreadyExistsException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        var user = userMapper.toEntity(request);
        user.setPassword(passwordConfig.passwordEncoder().encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public UserDto updateSingleUser(long Id, UpdateUserDto request) {
        var user = userRepository.findById(Id).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        userMapper.updateDto(request, user);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public void deleteSingleUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        userRepository.delete(user);
    }

    public void changeUserPassword(Long id, ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        if (!passwordConfig.passwordEncoder().matches(request.getOldPassword(), user.getPassword())) {
           throw new PasswordNotMatchingException("Old password doesn't match");
        }

        user.setPassword(passwordConfig.passwordEncoder().encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public trevisanvinicius.store.users.User findUser (Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }
}
