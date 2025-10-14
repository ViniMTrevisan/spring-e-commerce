package trevisanvinicius.store.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "unitPrice", target = "price") 
    OrderItemDTO toOrderDto(OrderItem orderItem);
}
