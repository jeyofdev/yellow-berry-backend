package com.jeyofdev.yellow_berry.domain.productToCart;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.cart.CartService;
import com.jeyofdev.yellow_berry.domain.productToCart.dto.ProductToCartDTO;
import com.jeyofdev.yellow_berry.domain.productToCart.dto.SaveProductToCartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/productToCart")
@RequiredArgsConstructor
public class ProductToCartController {
    private final ProductToCartMapper productToCartMapper;
    private final CartService cartService;
    private final ProductToCartService productToCartService;

    @PostMapping("/product/{productId}/cart/{cartId}")
    public ResponseEntity<DomainSuccessResponse<ProductToCartDTO>> productToCart(
            @PathVariable("productId") UUID productId,
            @PathVariable("cartId") UUID cartId,
            @RequestBody SaveProductToCartDTO saveProductToCartDTO
    ) {
        ProductToCart productToCart = productToCartMapper.mapToEntity(saveProductToCartDTO, cartService);
        ProductToCart newProductToCart = productToCartService.save(productId, cartId, productToCart);
        ProductToCartDTO newProductToCartDTO = productToCartMapper.mapFromEntity(newProductToCart);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newProductToCartDTO);
    }

    @PutMapping("/{productToCartId}")
    public ResponseEntity<DomainSuccessResponse<ProductToCartDTO>> updateProductToCartById(
            @PathVariable("productToCartId") UUID productToCartId,
            @RequestBody SaveProductToCartDTO saveProductToCartDTO
    ) {
        ProductToCart productToCart = productToCartMapper.mapToEntity(saveProductToCartDTO, cartService);
        ProductToCart updateProductToCart = productToCartService.updateById(productToCartId, productToCart);
        ProductToCartDTO updateProductToCartDTO = productToCartMapper.mapFromEntity(updateProductToCart);

        return DomainSuccessResponse.get(HttpStatus.OK, updateProductToCartDTO);
    }

    @DeleteMapping("/{productToCartId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteProductToCartById(@PathVariable("productToCartId") UUID productToCartId) {
        String message = productToCartService.deleteById(productToCartId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
