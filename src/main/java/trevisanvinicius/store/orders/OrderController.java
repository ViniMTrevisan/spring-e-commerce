package trevisanvinicius.store.orders;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trevisanvinicius.store.auth.AuthService;
import trevisanvinicius.store.users.UserService;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private AuthService authService;
    private UserService userService;
    private OrderMapper orderMapper;
    private OrderRepository orderRepository;
    private OrderService orderService;

    @GetMapping
    @Transactional()
    public ResponseEntity<?> getAllOrders() {

        var userId = authService.checkAuthenticated();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userService.findUser(userId);

        List<OrderResponseDTO> orderDtos = orderService.getOrderResponseDTOS(user);

        return ResponseEntity.ok(orderDtos);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(
            @PathVariable Long orderId) {

        var userId = authService.checkAuthenticated();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userService.findUser(userId);

        var order = orderService.getOrderByIdWithItems(orderId);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!orderService.checkCustomerAndUserIDs(order, user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(orderMapper.toOrderDto(order));
    }


}
