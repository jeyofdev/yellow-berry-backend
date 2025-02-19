package com.jeyofdev.yellow_berry.domain.productInformation.dto;

import com.jeyofdev.yellow_berry.core.enums.ColorEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;

import java.util.UUID;

public record ProductInformationDTO(
        UUID id,
        WeightEnum weight,
        String dimension,
        ColorEnum color,
        String brand,
        Integer quantity
) {
}
