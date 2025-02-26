package com.jeyofdev.yellow_berry.domain.productInformation.dto;

import com.jeyofdev.yellow_berry.core.enums.ColorEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;

public record SaveProductInformationDTO(
        WeightEnum weight,
        String dimension,
        ColorEnum color,
        Integer quantity
) {
}
