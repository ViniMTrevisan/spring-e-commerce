package trevisanvinicius.store.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@Getter
@Setter
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Iterable<ProductDto> getAllProducts(
            @RequestParam(required = false, name = "categoryId") Long categoryId
    ) {
        return productService.getAllProductsByCategory(categoryId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto request,
            UriComponentsBuilder uriBuilder
    ) {
        var productDto = productService.registerNewProduct(request);

        var uri =  uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();

        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable long id,
            @RequestBody ProductDto request) {

        var productDto = productService.updateSingleProduct(id, request);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteSingleProduct(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({ProductNotFoundException.class, CategoryNotFoundException.class})
    public ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }



}
