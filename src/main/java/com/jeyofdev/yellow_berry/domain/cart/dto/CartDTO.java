package com.jeyofdev.yellow_berry.domain.cart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductPreviewDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartDTO(
        UUID id,
        Date createdAt,
        Date updatedAt,
        ListResponseFormat<ProductPreviewDTO> products,
        ProfileDTO profile
) {
}
