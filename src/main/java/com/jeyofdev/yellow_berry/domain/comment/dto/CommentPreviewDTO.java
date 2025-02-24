package com.jeyofdev.yellow_berry.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfilePreviewDTO;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentPreviewDTO(
        UUID id,
        Integer rating,
        String body,
        ProfilePreviewDTO profile
) {
}
