package com.jeyofdev.yellow_berry.domain.wishlist;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WishlistService extends AbstractDomainService<WishList, WishlistRepository> {
    private final WishlistRepository wishListRepository;

    @Autowired
    public WishlistService(WishlistRepository wishListRepository) {
        super(wishListRepository, "Wishlist");
        this.wishListRepository = wishListRepository;
    }

    public WishList updateById(UUID wishlistId, WishList updatedWishlist) {
        WishList existingWishlist = findById(wishlistId);
        WishList existingWishlistUpdated = WishList.builder()
                .id(wishlistId)
                .name(updatedWishlist.getName() != null ? updatedWishlist.getName() : existingWishlist.getName())
                .build();

        return wishListRepository.save(existingWishlistUpdated);
    }

    public String deleteById(UUID wishlistId) {
        findById(wishlistId);
        wishListRepository.deleteById(wishlistId);

        return ConfirmMessage.WISHLIST_DELETE;
    }
}
