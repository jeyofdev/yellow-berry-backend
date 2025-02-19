package com.jeyofdev.yellow_berry.domain.cart.dto;

import java.util.Date;
import java.util.UUID;

public record CartDTO(
        UUID id,
        Date createdAt,
        Date updatedAt
) {
}
