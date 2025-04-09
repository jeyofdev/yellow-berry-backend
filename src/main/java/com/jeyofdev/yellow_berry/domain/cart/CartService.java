package com.jeyofdev.yellow_berry.domain.cart;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.domain.productToCart.ProductToCart;
import com.jeyofdev.yellow_berry.domain.productToCart.ProductToCartRepository;
import com.jeyofdev.yellow_berry.domain.profile.Profile;
import com.jeyofdev.yellow_berry.domain.profile.ProfileService;
import com.jeyofdev.yellow_berry.exception.AlreadyAssociatedException;
import com.jeyofdev.yellow_berry.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CartService extends AbstractDomainService<Cart, CartRepository> {
    private final CartRepository cartRepository;
    private final ProductToCartRepository productToCartRepository;
    private final ProfileService profileService;

    @Autowired
    public CartService(
            CartRepository cartRepository,
            ProductToCartRepository productToCartRepository,
            ProfileService profileService
    ) {
        super(cartRepository, "Cart");
        this.cartRepository = cartRepository;
        this.productToCartRepository = productToCartRepository;
        this.profileService = profileService;
    }

    public Cart save(UUID profileId, Cart cart) {
        Profile profile = profileService.findById(profileId);
        SecurityUtil.checkAuthenticatedUserOrAdminIsAuthorized(profile.getUser().getUsername(), false);

        if (profile.getCart() != null) {
            throw new AlreadyAssociatedException(MessageFormat.format(ErrorMessage.ALREADY_ASSOCIATED, "product", "cart"));
        }

        cart.setSubTotalPrice(0.00);
        cart.setTotalPrice(0.00);
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
        SecurityUtil.checkAuthenticatedUserOrAdminIsAuthorized(existingCart.getProfile().getUser().getUsername(), false);

        Double subTotalPrice = existingCart.getProductToCartList().stream()
                .mapToDouble(productToCart -> {
                    BigDecimal discountedPrice = BigDecimal.valueOf(productToCart.getProduct().getPrice() * (1 - productToCart.getProduct().getDiscount() / 100.0))
                            .setScale(2, RoundingMode.HALF_UP);
                    return discountedPrice.doubleValue() * productToCart.getQuantity();
                })
                .sum();

        double baseTVA = 20.0;
        Double amountTVA = subTotalPrice * (baseTVA / 100);
        Double totalPrice = BigDecimal.valueOf(subTotalPrice + amountTVA)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        existingCart.setUpdatedAt(new Date());
        existingCart.setSubTotalPrice(subTotalPrice);
        existingCart.setTotalPrice(totalPrice);

        return cartRepository.save(existingCart);

    }

    public String deleteById(UUID cartId) {
        Cart cart = findById(cartId);

        SecurityUtil.checkAuthenticatedUserOrAdminIsAuthorized(cart.getProfile().getUser().getUsername(), true);

        List<ProductToCart> productToCartList = productToCartRepository.findByCart(cart);

        if (cart.getProfile() != null) {
            cart.getProfile().setCart(null);
        }

        cartRepository.deleteById(cartId);

        return MessageFormat.format(ConfirmMessage.CONFIRM_DELETE, "cart");
    }
}
