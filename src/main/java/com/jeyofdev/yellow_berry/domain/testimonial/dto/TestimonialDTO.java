package com.jeyofdev.yellow_berry.domain.testimonial.dto;

import com.jeyofdev.yellow_berry.core.enums.JobEnum;

import java.util.UUID;

public record TestimonialDTO(
        UUID id,
        String firstname,
        String lastname,
        JobEnum job,
        String message
) {
}
