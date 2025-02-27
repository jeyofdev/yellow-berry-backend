package com.jeyofdev.yellow_berry.domain.cart;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.core.constant.Regex;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import com.jeyofdev.yellow_berry.domain.profile.Profile;
import com.jeyofdev.yellow_berry.domain.profile.ProfileService;
import com.jeyofdev.yellow_berry.exception.AlreadyAssociatedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

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
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile profile = profileService.findById(profileId);

        if (username.equals(profile.getUser().getUsername())) {
            if (profile.getCart() != null) {
                throw new AlreadyAssociatedException(MessageFormat.format(ErrorMessage.ALREADY_ASSOCIATED, "product", "cart"));
            }

            cart.setProfile(profile);
            cart.setCreatedAt(new Date());
            cart.setUpdatedAt(new Date());

            profile.setCart(cart);

            return cartRepository.save(cart);
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }

    public List<Cart> getCartListByIds(List<UUID> cartListIds) {
        if (cartListIds == null || cartListIds.contains(null)) {
            cartListIds = List.of();
        }

        return cartRepository.findAllById(cartListIds);
    }

    public Cart updateById(UUID cartId, Cart updatedCart) {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart existingCart = findById(cartId);

        if (username.equals(existingCart.getProfile().getUser().getUsername())) {
            existingCart.setUpdatedAt(new Date());
            return cartRepository.save(existingCart);
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }

    public String deleteById(UUID cartId) {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        Cart cart = findById(cartId);

        if (username.equals(cart.getProfile().getUser().getUsername()) || roles.equals("[ROLE_ADMIN]")) {
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
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }
}
