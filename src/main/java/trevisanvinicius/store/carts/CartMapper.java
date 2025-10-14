package trevisanvinicius.store.carts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "cartItems", target = "items")
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartResponseDTO toDto(Cart cart);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "price")
    CartItemResponseDTO toDto(CartItem cartItem);
}
