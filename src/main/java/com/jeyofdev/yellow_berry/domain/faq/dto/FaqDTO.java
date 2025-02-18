package com.jeyofdev.yellow_berry.domain.faq.dto;

import java.util.UUID;

public record FaqDTO(
        UUID id,
        String question,
        String answer
) {
}
