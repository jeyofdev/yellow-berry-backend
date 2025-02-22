package com.jeyofdev.yellow_berry.domain.wishlist.dto;

import com.jeyofdev.yellow_berry.domain.product.Product;

import java.util.List;
import java.util.UUID;

public record WishlistDTO(
        UUID id,
        String name,
        List<Product> productList
) {
}
