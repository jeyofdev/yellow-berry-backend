package com.jeyofdev.yellow_berry.domain.product;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.product.dto.SaveProductDTO;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<ProductDTO>>> findAllProducts() {
        List<Product> productList = productService.findAll();
        List<ProductDTO> productDTOList = productList.stream().map(productMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, productDTOList);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DomainSuccessResponse<ProductDTO>> findProductById(@PathVariable("productId") UUID productId) {
        Product product = productService.findById(productId);
        ProductDTO productDTO = productMapper.mapFromEntity(product);

        return DomainSuccessResponse.get(HttpStatus.OK, productDTO);
    }

    @PostMapping
    public ResponseEntity<DomainSuccessResponse<ProductDTO>> saveProduct(@RequestBody SaveProductDTO saveProductDTO) {
        Product product = productMapper.mapToEntity(saveProductDTO);
        Product newProduct = productService.save(product);
        ProductDTO newProductDTO = productMapper.mapFromEntity(newProduct);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newProductDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<DomainSuccessResponse<ProductDTO>> updateProductById(
            @PathVariable("productId") UUID productId,
            @RequestBody SaveProductDTO saveProductDTO
    ) {
        Product product = productMapper.mapToEntity(saveProductDTO);
        Product updateProduct = productService.updateById(productId, product);
        ProductDTO updateProductDTO = productMapper.mapFromEntity(updateProduct);

        return DomainSuccessResponse.get(HttpStatus.OK, updateProductDTO);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteProductById(@PathVariable("productId") UUID productId) {
        String message = productService.deleteById(productId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
