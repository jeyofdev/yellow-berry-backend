package com.jeyofdev.yellow_berry.domain.comment.dto;

public record SaveCommentDTO(
        String firstname,
        String lastname,
        Integer rating,
        String body
) {
}
