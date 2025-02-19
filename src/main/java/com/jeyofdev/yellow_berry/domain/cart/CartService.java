package com.jeyofdev.yellow_berry.domain.cart;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class CartService extends AbstractDomainService<Cart, CartRepository> {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        super(cartRepository, "Cart");
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart save(Cart cart) {
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());

        return cartRepository.save(cart);
    }

    public Cart updateById(UUID cartId, Cart updatedCart) {
        Cart existingCart = findById(cartId);
        Cart existingCartUpdated = Cart.builder()
                .id(cartId)
                .createdAt(existingCart.getCreatedAt())
                .updatedAt(new Date())
                .build();

        return cartRepository.save(existingCartUpdated);
    }

    public String deleteById(UUID cartId) {
        findById(cartId);
        cartRepository.deleteById(cartId);

        return ConfirmMessage.CART_DELETE;
    }
}
