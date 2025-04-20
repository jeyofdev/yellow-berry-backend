package com.jeyofdev.yellow_berry.domain.productInformation.dto;

import java.util.List;
import java.util.UUID;

public record ProductInformationPreviewDTO(
        UUID id,
        List<String> weightList,
        List<String> colorList
) {
}
