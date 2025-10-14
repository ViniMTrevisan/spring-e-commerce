package trevisanvinicius.store.carts;

import trevisanvinicius.store.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    @Modifying
    List<CartItem> deleteAllByCart(Cart cart);
}
