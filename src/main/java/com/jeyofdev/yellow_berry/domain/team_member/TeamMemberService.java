package com.jeyofdev.yellow_berry.domain.team_member;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeamMemberService extends AbstractDomainService<TeamMember, TeamMemberRepository> {
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        super(teamMemberRepository, "TeamMember");
        this.teamMemberRepository = teamMemberRepository;
    }

    public TeamMember updateById(UUID teamId, TeamMember updatedTeamMember) {
        TeamMember existingTeamMember = findById(teamId);
        TeamMember existingTeamMemberUpdated = TeamMember.builder()
                .id(teamId)
                .firstname(updatedTeamMember.getFirstname() != null ? updatedTeamMember.getFirstname() : existingTeamMember.getFirstname())
                .lastname(updatedTeamMember.getLastname() != null ? updatedTeamMember.getLastname() : existingTeamMember.getLastname())
                .job(updatedTeamMember.getJob() != null ? updatedTeamMember.getJob() : existingTeamMember.getJob())
                .twitter(updatedTeamMember.getTwitter() != null ? updatedTeamMember.getTwitter() : existingTeamMember.getTwitter())
                .instagram(updatedTeamMember.getInstagram() != null ? updatedTeamMember.getInstagram() : existingTeamMember.getInstagram())
                .linkedin(updatedTeamMember.getLinkedin() != null ? updatedTeamMember.getLinkedin() : existingTeamMember.getLinkedin())
                .build();

        return teamMemberRepository.save(existingTeamMemberUpdated);
    }

    public String deleteById(UUID teamMemberId) {
        findById(teamMemberId);
        teamMemberRepository.deleteById(teamMemberId);

        return ConfirmMessage.TEAM_DELETE;
    }
}
