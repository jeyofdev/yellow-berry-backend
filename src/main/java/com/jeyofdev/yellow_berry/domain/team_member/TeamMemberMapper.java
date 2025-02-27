package com.jeyofdev.yellow_berry.domain.team_member;

import com.jeyofdev.yellow_berry.domain.team_member.dto.SaveTeamMemberDTO;
import com.jeyofdev.yellow_berry.domain.team_member.dto.TeamMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMemberMapper {
    @Mapping(source = "twitter", target = "social.twitter")
    @Mapping(source = "instagram", target = "social.instagram")
    @Mapping(source = "linkedin", target = "social.linkedin")
    @Mapping(target = "name", expression = "java(teamMember.getFirstname() + \" \" + teamMember.getLastname())")
    TeamMemberDTO mapFromEntity(TeamMember teamMember);

    TeamMember mapToEntity(SaveTeamMemberDTO saveTeamMemberDTO);
}
