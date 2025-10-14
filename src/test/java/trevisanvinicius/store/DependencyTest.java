package trevisanvinicius.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import trevisanvinicius.store.auth.SecurityConfig;
import trevisanvinicius.store.users.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DependencyTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testBeansAreCreatedWithoutCircularDependency() {
        System.out.println("--- INICIANDO TESTE DE DEPENDÊNCIA ---");

        System.out.println("PasswordEncoder Injetado: " + passwordEncoder);
        assertNotNull(passwordEncoder, "FALHA: O bean PasswordEncoder não foi criado.");

        System.out.println("UserService Injetado: " + userService);
        assertNotNull(userService, "FALHA: O bean UserService não foi criado.");

        System.out.println("SecurityConfig Injetado: " + securityConfig);
        assertNotNull(securityConfig, "FALHA: O bean SecurityConfig não foi criado.");

        System.out.println("--- SUCESSO: Todos os beans foram criados e injetados sem erro de ciclo! ---");
    }
}