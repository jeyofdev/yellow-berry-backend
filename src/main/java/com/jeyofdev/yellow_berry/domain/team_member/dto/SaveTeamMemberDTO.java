package com.jeyofdev.yellow_berry.domain.team_member.dto;

import com.jeyofdev.yellow_berry.core.enums.JobEnum;

public record SaveTeamMemberDTO(
        String firstname,
        String lastname,
        JobEnum job,
        String twitter,
        String instagram,
        String linkedin
) {
}
