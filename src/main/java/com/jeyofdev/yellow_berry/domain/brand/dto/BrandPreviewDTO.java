package com.jeyofdev.yellow_berry.domain.brand.dto;

import com.jeyofdev.yellow_berry.core.enums.ColorEnum;

import java.util.UUID;

public record BrandPreviewDTO(
        UUID id,
        String name,
        ColorEnum color
) {
}
