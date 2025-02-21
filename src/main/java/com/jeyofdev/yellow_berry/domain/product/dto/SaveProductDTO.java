package com.jeyofdev.yellow_berry.domain.product.dto;

import com.jeyofdev.yellow_berry.core.enums.StockEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.domain.comment.dto.CommentDTO;

import java.util.List;
import java.util.UUID;

public record SaveProductDTO(
        String name,
        Integer rating,
        String description,
        Double price,
        Double priceDiscount,
        Double discount,
        StockEnum stock,
        WeightEnum weight,
        List<UUID> tagIds,
        List<UUID> categoryIds,
        List<UUID> commentIds
) {
}
