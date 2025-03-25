package com.jeyofdev.yellow_berry.domain.product.dto;

import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.core.interfaces.domain.model.HasPriceDetails;
import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.core.model.PriceFormat;
import com.jeyofdev.yellow_berry.domain.category.Category;

import java.util.UUID;

public record ProductPreviewDTO(
        UUID id,
        String name,
        Integer rating,
        WeightEnum weight,
        PriceFormat priceDetails,
        ListResponseFormat<Category> categories,
        Integer commentCount
) implements HasPriceDetails {

}