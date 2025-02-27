package com.jeyofdev.yellow_berry.domain.wishlist;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.domain.cart.Cart;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import com.jeyofdev.yellow_berry.domain.profile.Profile;
import com.jeyofdev.yellow_berry.domain.profile.ProfileService;
import com.jeyofdev.yellow_berry.exception.AlreadyTakenException;
import com.jeyofdev.yellow_berry.exception.AlreadyAssociatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class WishlistService extends AbstractDomainService<WishList, WishlistRepository> {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final ProfileService profileService;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository, ProfileService profileService) {
        super(wishlistRepository, "Wishlist");
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.profileService = profileService;
    }

    public List<WishList> getWishlistsByIds(List<UUID> wishlistIds) {
        if (wishlistIds == null || wishlistIds.contains(null)) {
            wishlistIds = List.of();
        }

        return wishlistRepository.findAllById(wishlistIds);
    }

    public WishList save(UUID profileId, WishList wishlist) {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile profile = profileService.findById(profileId);

        if (username.equals(profile.getUser().getUsername())) {
            if (profile.getWishlist() != null) {
                throw new AlreadyAssociatedException(MessageFormat.format(ErrorMessage.ALREADY_ASSOCIATED, "profile", "wishlist"));
            }

            if (wishlistRepository.existsByName(wishlist.getName())) {
                throw new AlreadyTakenException(MessageFormat.format(ErrorMessage.ALREADY_TAKEN, entityName, "name", wishlist.getName()));
            }

            wishlist.setProfile(profile);
            profile.setWishlist(wishlist);

            return wishlistRepository.save(wishlist);
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }

    public WishList updateById(UUID wishlistId, WishList updatedWishlist) {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        WishList existingWishlist = findById(wishlistId);

        if (username.equals(existingWishlist.getProfile().getUser().getUsername())) {
            existingWishlist.setName(updatedWishlist.getName() != null ? updatedWishlist.getName() : existingWishlist.getName());

            return wishlistRepository.save(existingWishlist);
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }

    public String deleteById(UUID wishlistId) {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        WishList wishlist = findById(wishlistId);

        if (username.equals(wishlist.getProfile().getUser().getUsername()) || roles.equals("[ROLE_ADMIN]")) {
            List<Product> productList = productRepository.findByWishlist(wishlist);

            for (Product product : productList) {
                product.getWishlists().remove(wishlist);
                productRepository.save(product);
            }

            wishlistRepository.deleteById(wishlistId);

            return ConfirmMessage.WISHLIST_DELETE;
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }
}
