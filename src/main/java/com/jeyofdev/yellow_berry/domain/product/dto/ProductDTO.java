package com.jeyofdev.yellow_berry.domain.product.dto;

import com.jeyofdev.yellow_berry.core.enums.StockEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;

import java.util.UUID;

public record ProductDTO(
        UUID id,
        String name,
        Integer rating,
        String description,
        Double price,
        Double priceDiscount,
        Double discount,
        StockEnum stock,
        WeightEnum weight
) {
}
