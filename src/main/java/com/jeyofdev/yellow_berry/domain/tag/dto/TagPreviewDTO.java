package com.jeyofdev.yellow_berry.domain.tag.dto;

import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductPreviewDTO;

import java.util.UUID;

public record TagPreviewDTO(
        UUID id,
        String name,
        ListResponseFormat<ProductPreviewDTO> products
) {
}
