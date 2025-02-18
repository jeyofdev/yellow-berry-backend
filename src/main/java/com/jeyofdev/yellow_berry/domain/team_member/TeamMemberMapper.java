package com.jeyofdev.yellow_berry.domain.team_member;

import com.jeyofdev.yellow_berry.domain.team_member.dto.TeamMemberDTO;
import com.jeyofdev.yellow_berry.domain.team_member.dto.SaveTeamMemberDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMemberMapper {
    TeamMemberDTO mapFromEntity(TeamMember teamMember);
    TeamMember mapToEntity(SaveTeamMemberDTO saveTeamMemberDTO);
}
