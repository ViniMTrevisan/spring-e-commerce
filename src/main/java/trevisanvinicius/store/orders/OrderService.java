package trevisanvinicius.store.orders;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import trevisanvinicius.store.carts.Cart;
import trevisanvinicius.store.users.User;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Order createOrder(User user, Cart cart) {
        Order order = new Order();
        order.setStatus(PaymentStatus.PENDING);
        order.setCustomerId(user);
        order.setTotalPrice(cart.getTotalPrice());

        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem item = new OrderItem();
                    item.setProduct(cartItem.getProduct());
                    item.setQuantity(cartItem.getQuantity());
                    item.setUnitPrice(cartItem.getProduct().getPrice());
                    item.setTotalPrice(cartItem.getTotalPrice());
                    item.setOrder(order);
                    return item;
                })
                .toList();

        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }

    public List<OrderResponseDTO> getOrderResponseDTOS(User user) {
        List<Order> orders = orderRepository.findByCustomerIdWithDetails(user);

        return orders.stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    public Order getOrderByIdWithItems(Long orderId) {
        return orderRepository.findByIdWithItemsAndProducts(orderId).orElse(null);
    }

    public boolean checkCustomerAndUserIDs(Order order, User user) {
        return order.getCustomerId().getId().equals(user.getId());
    }

}
