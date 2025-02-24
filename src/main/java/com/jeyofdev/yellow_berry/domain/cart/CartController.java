package com.jeyofdev.yellow_berry.domain.cart;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.cart.dto.CartDTO;
import com.jeyofdev.yellow_berry.domain.cart.dto.CartPreviewDTO;
import com.jeyofdev.yellow_berry.domain.cart.dto.SaveCartDTO;
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

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<CartDTO>>> findAllCarts() {
        List<Cart> tagList = cartService.findAll();
        List<CartDTO> cart = tagList.stream().map(cartMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, cart);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<DomainSuccessResponse<CartDTO>> findCartById(@PathVariable("cartId") UUID cartId) {
        Cart cart = cartService.findById(cartId);
        CartDTO cartDTO = cartMapper.mapFromEntity(cart);

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
        CartDTO updateCartDTO = cartMapper.mapFromEntity(updateCart);

        return DomainSuccessResponse.get(HttpStatus.OK, updateCartDTO);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteCartById(@PathVariable("cartId") UUID cartId) {
        String message = cartService.deleteById(cartId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);
    }
}
