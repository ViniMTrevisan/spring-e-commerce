package trevisanvinicius.store.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDto request);

    @Mapping(target = "id",  ignore = true)
    void updateDto(ProductDto productDto, @MappingTarget Product Product);
}
