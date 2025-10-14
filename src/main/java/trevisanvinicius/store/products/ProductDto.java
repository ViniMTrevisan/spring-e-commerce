package trevisanvinicius.store.products;


import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private byte categoryId;
}
