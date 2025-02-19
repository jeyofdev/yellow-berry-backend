package com.jeyofdev.yellow_berry.domain.productDetails.dto;

import java.util.UUID;

public record ProductDetailsDTO(
        UUID id,
        String description,
        String seller,
        String service
) {
}
