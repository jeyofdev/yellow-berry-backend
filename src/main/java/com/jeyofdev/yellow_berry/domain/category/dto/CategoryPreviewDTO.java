package com.jeyofdev.yellow_berry.domain.category.dto;

import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductDTO;

import java.util.UUID;

public record CategoryPreviewDTO(
        UUID id,
        String name,
        ListResponseFormat<ProductDTO> products
) {
}
