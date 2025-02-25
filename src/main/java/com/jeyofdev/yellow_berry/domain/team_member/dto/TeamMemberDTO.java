package com.jeyofdev.yellow_berry.domain.team_member.dto;

import com.jeyofdev.yellow_berry.core.enums.JobEnum;
import com.jeyofdev.yellow_berry.core.model.SocialFormat;

import java.util.UUID;

public record TeamMemberDTO(
        UUID id,
        String name,
        JobEnum job,
        SocialFormat social
) {
}
