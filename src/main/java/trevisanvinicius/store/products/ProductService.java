package trevisanvinicius.store.products;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductDto findProductById(Long productId) {
        Optional<Product> prodId = productRepository.findById(productId);
        if (prodId.isEmpty()) {
            throw new ProductNotFoundException("Product was not found with the given id");
        }
        return productMapper.toDto(prodId.get());
    }

    public Iterable<ProductDto> getAllProductsByCategory(Long categoryId) {
        List<Product> products;

        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
            if (products.isEmpty()){
                throw new CategoryNotFoundException("Category was not found with the given id");
            }
        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDto registerNewProduct(ProductDto request) {
        var product = productMapper.toEntity(request);

        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            throw new CategoryNotFoundException("Category not found with the given id: " + request.getCategoryId());
        }
        product.setCategory(category);
        productRepository.save(product);

        return productMapper.toDto(product);
    }

    public ProductDto updateSingleProduct(long id, ProductDto request) {
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            throw new CategoryNotFoundException("Category not found with id " + request.getCategoryId());
        }

        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id " + id);
        }

        productMapper.updateDto(request, product);
        product.setCategory(category);
        productRepository.save(product);
        request.setId(product.getId());

        return request;
    }

    public void deleteSingleProduct(long id) {
        var product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product not found with id " + id));

        productRepository.delete(product);
    }

    public Product findProductEntityById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("Product not found with id " + productId));
    }
}
