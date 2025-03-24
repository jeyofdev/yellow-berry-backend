package com.jeyofdev.yellow_berry.domain.brand.dto;

import com.jeyofdev.yellow_berry.core.enums.ColorEnum;

public record SaveBrandDTO(
        String name,
        ColorEnum color
) {
}
