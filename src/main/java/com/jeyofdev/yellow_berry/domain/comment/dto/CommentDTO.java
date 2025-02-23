package com.jeyofdev.yellow_berry.domain.comment.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentDTO(
        UUID id,
        Integer rating,
        String body,
        ProfileDTO profile
) {
}
