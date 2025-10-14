package trevisanvinicius.store.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import trevisanvinicius.store.payments.CheckoutResponseDTO;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    CheckoutResponseDTO toDto(Order order);

    @Mapping(source = "orderItems", target = "items")
    OrderResponseDTO toOrderDto(Order order);

}
