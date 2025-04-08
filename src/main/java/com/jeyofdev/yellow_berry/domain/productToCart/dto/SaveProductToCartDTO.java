package com.jeyofdev.yellow_berry.domain.productToCart.dto;

import com.jeyofdev.yellow_berry.core.enums.WeightEnum;

public record SaveProductToCartDTO(
        Integer quantity,
        WeightEnum weight,
        Double price,
        Integer discount
) {
}
