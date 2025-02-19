package com.jeyofdev.yellow_berry.domain.about.dto;

import java.util.UUID;

public record AboutDTO(
        UUID id,
        String title,
        String subtitle,
        String description
) {
}
