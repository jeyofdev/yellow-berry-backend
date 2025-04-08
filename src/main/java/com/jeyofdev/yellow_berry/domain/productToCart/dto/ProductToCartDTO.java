package com.jeyofdev.yellow_berry.domain.productToCart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeyofdev.yellow_berry.core.interfaces.domain.model.HasPriceDetails;
import com.jeyofdev.yellow_berry.core.model.PriceFormat;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductToCartDTO(
        UUID id,
        Integer quantity,
        String weight,
        String name,
        PriceFormat priceDetails
) implements HasPriceDetails {
}
