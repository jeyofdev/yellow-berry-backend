package com.jeyofdev.yellow_berry.domain.wishlist;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WishlistService extends AbstractDomainService<WishList, WishlistRepository> {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        super(wishlistRepository, "Wishlist");
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public List<WishList> getWishlistsByIds(List<UUID> wishlistIds) {
        if (wishlistIds == null || wishlistIds.contains(null)) {
            wishlistIds = List.of();
        }

        return wishlistRepository.findAllById(wishlistIds);
    }

    public WishList updateById(UUID wishlistId, WishList updatedWishlist) {
        WishList existingWishlist = findById(wishlistId);
        WishList existingWishlistUpdated = WishList.builder()
                .id(wishlistId)
                .name(updatedWishlist.getName() != null ? updatedWishlist.getName() : existingWishlist.getName())
                .build();

        return wishlistRepository.save(existingWishlistUpdated);
    }

    public String deleteById(UUID wishlistId) {
        WishList wishlist = findById(wishlistId);
        List<Product> productList = productRepository.findByWishlist(wishlist);

        for (Product product : productList) {
            product.getWishlists().remove(wishlist);
            productRepository.save(product);
        }

        findById(wishlistId);
        wishlistRepository.deleteById(wishlistId);

        return ConfirmMessage.WISHLIST_DELETE;
    }
}
