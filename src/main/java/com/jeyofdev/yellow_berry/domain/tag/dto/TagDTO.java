package com.jeyofdev.yellow_berry.domain.tag.dto;

import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.product.Product;

import java.util.UUID;

public record TagDTO(
        UUID id,
        String name,
        ListResponseFormat<Product> products
) {
}
