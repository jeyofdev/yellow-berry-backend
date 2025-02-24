package com.jeyofdev.yellow_berry.domain.cart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartPreviewDTO(
        UUID id,
        Date createdAt,
        Date updatedAt,
        ProfileDTO profile
) {
}
