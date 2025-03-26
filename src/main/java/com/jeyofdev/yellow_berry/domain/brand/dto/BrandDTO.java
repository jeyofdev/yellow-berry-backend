package com.jeyofdev.yellow_berry.domain.brand.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductPreviewDTO;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BrandDTO(
        UUID id,
        String name,
        String color,
        ListResponseFormat<ProductPreviewDTO> products
) {
}
