package com.jeyofdev.yellow_berry.domain.cart;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import com.jeyofdev.yellow_berry.domain.profile.Profile;
import com.jeyofdev.yellow_berry.domain.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CartService extends AbstractDomainService<Cart, CartRepository> {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProfileService profileService;

    @Autowired
    public CartService(
            CartRepository cartRepository,
            ProductRepository productRepository,
            ProfileService profileService
    ) {
        super(cartRepository, "Cart");
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.profileService = profileService;
    }

    public Cart save(UUID profileId, Cart cart) {
        Profile profile = profileService.findById(profileId);

        cart.setProfile(profile);
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());

        profile.setCart(cart);

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
        existingCart.setUpdatedAt(new Date());

        return cartRepository.save(existingCart);
    }

    public String deleteById(UUID cartId) {
        Cart cart = findById(cartId);
        List<Product> productList = productRepository.findByCartList(cart);

        for (Product product : productList) {
            product.getCartList().remove(cart);
            productRepository.save(product);
        }

        if (cart.getProfile() != null) {
            cart.getProfile().setCart(null);
        }

        cartRepository.deleteById(cartId);

        return ConfirmMessage.CART_DELETE;
    }
}
