package com.jeyofdev.yellow_berry.domain.comment.dto;


import java.util.UUID;

public record CommentDTO(
        UUID id,
        String firstname,
        String lastname,
        Integer rating,
        String body
) {
}
