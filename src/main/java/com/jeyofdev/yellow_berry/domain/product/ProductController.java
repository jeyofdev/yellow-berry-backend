package com.jeyofdev.yellow_berry.domain.product;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.brand.BrandService;
import com.jeyofdev.yellow_berry.domain.cart.CartService;
import com.jeyofdev.yellow_berry.domain.category.CategoryService;
import com.jeyofdev.yellow_berry.domain.comment.CommentMapper;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductDTO;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductPreviewDTO;
import com.jeyofdev.yellow_berry.domain.product.dto.SaveProductDTO;
import com.jeyofdev.yellow_berry.domain.tag.TagService;
import com.jeyofdev.yellow_berry.domain.wishlist.WishlistService;
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
    private final TagService tagService;
    private final CategoryService categoryService;
    private final WishlistService wishlistService;
    private final CartService cartService;
    private final BrandService brandService;
    private final CommentMapper commentMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<ProductPreviewDTO>>> findAllProducts() {
        List<Product> productList = productService.findAll();
        List<ProductPreviewDTO> productPreviewDTOList = productList.stream().map(productMapper::mapFromEntityPreview).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, productPreviewDTOList);
    }

    @GetMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<DomainSuccessResponse<List<ProductPreviewDTO>>> findByCategoryIdOrderedByIdExcludingProductId(
            @PathVariable("productId") UUID productId,
            @PathVariable("categoryId") UUID categoryId
    ) {
        List<Product> productList = productService.findByCategoryIdOrderedByIdExcludingProductId(categoryId, productId);
        List<ProductPreviewDTO> productPreviewDTOList = productList.stream().map(productMapper::mapFromEntityPreview).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, productPreviewDTOList);
    }

    @GetMapping("/last")
    public ResponseEntity<DomainSuccessResponse<List<ProductPreviewDTO>>> findLatestProducts() {
        List<Product> productList = productService.findLatestProducts();
        List<ProductPreviewDTO> productPreviewDTOList = productList.stream().map(productMapper::mapFromEntityPreview).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, productPreviewDTOList);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DomainSuccessResponse<ProductDTO>> findProductById(@PathVariable("productId") UUID productId) {
        Product product = productService.findById(productId);
        ProductDTO productDTO = productMapper.mapFromEntity(product, commentMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, productDTO);
    }

    @PostMapping
    public ResponseEntity<DomainSuccessResponse<ProductPreviewDTO>> saveProduct(@RequestBody SaveProductDTO saveProductDTO) {
        Product product = productMapper.mapToEntity(saveProductDTO, tagService, categoryService, wishlistService, cartService, brandService);
        Product newProduct = productService.save(product);
        ProductPreviewDTO newProductPreviewDTO = productMapper.mapFromEntityPreview(newProduct);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newProductPreviewDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<DomainSuccessResponse<ProductDTO>> updateProductById(
            @PathVariable("productId") UUID productId,
            @RequestBody SaveProductDTO saveProductDTO
    ) {
        Product product = productMapper.mapToEntity(saveProductDTO, tagService, categoryService, wishlistService, cartService, brandService);
        Product updateProduct = productService.updateById(productId, product);
        ProductDTO updateProductDTO = productMapper.mapFromEntity(updateProduct, commentMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, updateProductDTO);
    }

    @PostMapping("/{productId}/wishlist/{wishlistId}")
    public ResponseEntity<DomainSuccessResponse<Object>> addOrRemoveProductToWishlist(
            @PathVariable("productId") UUID productId,
            @PathVariable("wishlistId") UUID wishlistId
    ) {
        Product product = productService.addOrRemoveProductToWishlist(productId, wishlistId);
        ProductDTO productDTO = productMapper.mapFromEntity(product, commentMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, productDTO);
    }

    @PostMapping("/{productId}/add/cart/{cartId}")
    public ResponseEntity<DomainSuccessResponse<Object>> addProductToCart(
            @PathVariable("productId") UUID productId,
            @PathVariable("cartId") UUID cartId
    ) {
        Product product = productService.addProductToCart(productId, cartId);
        ProductDTO productDTO = productMapper.mapFromEntity(product, commentMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, productDTO);
    }

    @PostMapping("/{productId}/remove/cart/{cartId}")
    public ResponseEntity<DomainSuccessResponse<Object>> addRemoveToCart(
            @PathVariable("productId") UUID productId,
            @PathVariable("cartId") UUID cartId
    ) {
        Product product = productService.removeProductToCart(productId, cartId);
        ProductDTO productDTO = productMapper.mapFromEntity(product, commentMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, productDTO);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteProductById(@PathVariable("productId") UUID productId) {
        String message = productService.deleteById(productId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
