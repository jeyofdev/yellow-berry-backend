package com.jeyofdev.yellow_berry.domain.wishlist.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductPreviewDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WishlistPreviewDTO(
        UUID id,
        String name,
        ListResponseFormat<ProductPreviewDTO> products,
        ProfileDTO profile
) {
}
