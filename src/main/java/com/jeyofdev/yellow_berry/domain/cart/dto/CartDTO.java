package com.jeyofdev.yellow_berry.domain.cart.dto;

import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductDTO;
import com.jeyofdev.yellow_berry.domain.profile.Profile;

import java.util.Date;
import java.util.UUID;

public record CartDTO(
        UUID id,
        Date createdAt,
        Date updatedAt,
        ListResponseFormat<ProductDTO> productList,
        Profile profile
) {
}
