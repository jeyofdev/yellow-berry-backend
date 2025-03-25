package com.jeyofdev.yellow_berry.domain.product.dto;

import com.jeyofdev.yellow_berry.core.enums.StockEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;

import java.util.List;
import java.util.UUID;

public record SaveProductDTO(
        String name,
        Integer rating,
        Double price,
        Integer discount,
        StockEnum stock,
        WeightEnum weight,
        List<UUID> tagIds,
        List<UUID> categoryIds,
        List<UUID> commentIds,
        List<UUID> wishlistIds,
        List<UUID> cartListIds,
        UUID brandId
) {
}
