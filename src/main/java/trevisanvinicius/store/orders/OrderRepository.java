package trevisanvinicius.store.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trevisanvinicius.store.users.User;
import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.orderItems i " +
            "LEFT JOIN FETCH i.product p " +
            "LEFT JOIN FETCH p.category " +
            "WHERE o.customerId = :customerId")
    List<Order> findByCustomerIdWithDetails(User customerId);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems i LEFT JOIN FETCH i.product p " +
            "LEFT JOIN FETCH p.category WHERE o.id = :orderId")
    Optional<Order> findByIdWithItemsAndProducts(@Param("orderId") Long orderId);
}
