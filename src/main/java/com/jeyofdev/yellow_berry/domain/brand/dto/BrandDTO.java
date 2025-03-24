package com.jeyofdev.yellow_berry.domain.brand.dto;

import com.jeyofdev.yellow_berry.core.enums.ColorEnum;
import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductPreviewDTO;

import java.util.UUID;

public record BrandDTO(
        UUID id,
        String name,
        ColorEnum color,
        ListResponseFormat<ProductPreviewDTO> products
) {
}
