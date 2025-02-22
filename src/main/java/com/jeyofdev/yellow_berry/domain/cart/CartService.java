package com.jeyofdev.yellow_berry.domain.cart;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import com.jeyofdev.yellow_berry.domain.wishlist.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CartService extends AbstractDomainService<Cart, CartRepository> {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        super(cartRepository, "Cart");
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart save(Cart cart) {
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());

        return cartRepository.save(cart);
    }

    public List<Cart> getCartListByIds(List<UUID> cartListIds) {
        if (cartListIds == null || cartListIds.contains(null)) {
            cartListIds = List.of();
        }

        return cartRepository.findAllById(cartListIds);
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
        Cart cart = findById(cartId);
        List<Product> productList = productRepository.findByCartList(cart);

        for (Product product : productList) {
            product.getCartList().remove(cart);
            productRepository.save(product);
        }

        cartRepository.deleteById(cartId);

        return ConfirmMessage.CART_DELETE;
    }
}
