package com.jeyofdev.yellow_berry.domain.brand.dto;

import com.jeyofdev.yellow_berry.domain.product.Product;

import java.util.List;
import java.util.UUID;

public record BrandDTO(
        UUID id,
        String name,
        List<Product> productList
) {
}
