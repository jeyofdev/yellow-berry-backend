package com.jeyofdev.yellow_berry.domain.wishlist.dto;

import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.profile.Profile;

import java.util.UUID;

public record WishlistDTO(
        UUID id,
        String name,
        ListResponseFormat<Product> products,
        Profile profile
) {
}
