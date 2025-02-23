package com.jeyofdev.yellow_berry.domain.comment.dto;


import com.jeyofdev.yellow_berry.domain.profile.Profile;

import java.util.UUID;

public record CommentDTO(
        UUID id,
        Integer rating,
        String body,
        Profile profile
) {
}
