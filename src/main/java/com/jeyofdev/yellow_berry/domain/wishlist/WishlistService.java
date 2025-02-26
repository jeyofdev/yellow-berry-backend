package com.jeyofdev.yellow_berry.domain.wishlist;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import com.jeyofdev.yellow_berry.domain.profile.Profile;
import com.jeyofdev.yellow_berry.domain.profile.ProfileService;
import com.jeyofdev.yellow_berry.exception.AlreadyTakenException;
import com.jeyofdev.yellow_berry.exception.AlreadyAssociatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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
        Profile profile = profileService.findById(profileId);

        if (profile.getWishlist() != null) {
            throw new AlreadyAssociatedException(MessageFormat.format(ErrorMessage.ALREADY_ASSOCIATED, "profile", "wishlist"));
        }

        if (wishlistRepository.existsByName(wishlist.getName())) {
            throw new AlreadyTakenException(MessageFormat.format(ErrorMessage.ALREADY_TAKEN, entityName, "name", wishlist.getName()));
        }


        wishlist.setProfile(profile);
        profile.setWishlist(wishlist);

        return wishlistRepository.save(wishlist);
    }

    public WishList updateById(UUID wishlistId, WishList updatedWishlist) {
        WishList existingWishlist = findById(wishlistId);
        existingWishlist.setName(updatedWishlist.getName() != null ? updatedWishlist.getName() : existingWishlist.getName());

        return wishlistRepository.save(existingWishlist);
    }

    public String deleteById(UUID wishlistId) {
        WishList wishlist = findById(wishlistId);
        List<Product> productList = productRepository.findByWishlist(wishlist);

        for (Product product : productList) {
            product.getWishlists().remove(wishlist);
            productRepository.save(product);
        }

        wishlistRepository.deleteById(wishlistId);

        return ConfirmMessage.WISHLIST_DELETE;
    }
}
