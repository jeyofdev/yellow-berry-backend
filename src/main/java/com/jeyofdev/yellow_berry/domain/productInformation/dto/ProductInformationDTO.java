package com.jeyofdev.yellow_berry.domain.productInformation.dto;

import java.util.List;
import java.util.UUID;

public record ProductInformationDTO(
        UUID id,
        List<String> weightList,
        String dimension,
        List<String> colorList,
        Integer quantity
) {
}
