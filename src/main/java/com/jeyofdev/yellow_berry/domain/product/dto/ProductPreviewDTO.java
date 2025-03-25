package com.jeyofdev.yellow_berry.domain.product.dto;

import com.jeyofdev.yellow_berry.core.interfaces.domain.model.HasPriceDetails;
import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.core.model.PriceFormat;
import com.jeyofdev.yellow_berry.domain.category.Category;

import java.util.UUID;

public record ProductPreviewDTO(
        UUID id,
        String name,
        String reference,
        String description,
        Integer rating,
        String weight,
        PriceFormat priceDetails,
        ListResponseFormat<Category> categories,
        Integer commentCount
) implements HasPriceDetails {

}