package com.jeyofdev.yellow_berry.domain.service.dto;

import java.util.UUID;

public record ServiceDTO(
        UUID id,
        String name,
        String description
) {
}
