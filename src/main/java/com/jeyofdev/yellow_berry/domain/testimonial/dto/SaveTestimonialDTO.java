package com.jeyofdev.yellow_berry.domain.testimonial.dto;

import com.jeyofdev.yellow_berry.core.enums.JobEnum;

public record SaveTestimonialDTO(
        String firstname,
        String lastname,
        JobEnum job,
        String message
) {
}
