package com.jeyofdev.yellow_berry.domain.cart;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.cart.dto.CartDTO;
import com.jeyofdev.yellow_berry.domain.cart.dto.CartPreviewDTO;
import com.jeyofdev.yellow_berry.domain.cart.dto.SaveCartDTO;
import com.jeyofdev.yellow_berry.domain.productToCart.ProductToCartMapper;
import com.jeyofdev.yellow_berry.domain.profile.Profile;
import com.jeyofdev.yellow_berry.domain.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;
    private final ProductToCartMapper productToCartMapper;
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<CartDTO>>> findAllCarts() {
        List<Cart> cartList = cartService.findAll();
        List<CartDTO> cartDTO = cartList.stream().map(cart -> cartMapper.mapFromEntity(cart, productToCartMapper)).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, cartDTO);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<DomainSuccessResponse<CartDTO>> findCartById(@PathVariable("cartId") UUID cartId) {
        Cart cart = cartService.findById(cartId);
        CartDTO cartDTO = cartMapper.mapFromEntity(cart, productToCartMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, cartDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DomainSuccessResponse<CartDTO>> findCartByUserId(@PathVariable("userId") UUID userId) {
        Profile profile = profileService.findByUserId(userId);
        Cart cart = cartService.findById(profile.getCart().getId());
        CartDTO cartDTO = cartMapper.mapFromEntity(cart, productToCartMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, cartDTO);
    }

    @PostMapping("/profile/{profileId}")
    public ResponseEntity<DomainSuccessResponse<CartPreviewDTO>> saveCart(
            @PathVariable("profileId") UUID profileId,
            @RequestBody SaveCartDTO saveCartDTO
    ) {
        Cart cart = cartMapper.mapToEntity(saveCartDTO);
        Cart newCart = cartService.save(profileId, cart);
        CartPreviewDTO newCartPreviewDTO = cartMapper.mapFromEntityPreview(newCart);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newCartPreviewDTO);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<DomainSuccessResponse<CartDTO>> updateCartById(
            @PathVariable("cartId") UUID cartId,
            @RequestBody SaveCartDTO saveCartDTO
    ) {
        Cart cart = cartMapper.mapToEntity(saveCartDTO);
        Cart updateCart = cartService.updateById(cartId, cart);
        CartDTO updateCartDTO = cartMapper.mapFromEntity(updateCart, productToCartMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, updateCartDTO);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteCartById(@PathVariable("cartId") UUID cartId) {
        String message = cartService.deleteById(cartId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);
    }
}
