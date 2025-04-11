package com.jeyofdev.yellow_berry.domain.category.dto;

import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductPreviewDTO;

import java.util.UUID;

public record CategoryDTO(
        UUID id,
        String name,
        ListResponseFormat<ProductPreviewDTO> products
) {
}
