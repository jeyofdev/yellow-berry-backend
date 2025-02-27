package com.jeyofdev.yellow_berry.domain.team_member;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.exception.AlreadyTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
public class TeamMemberService extends AbstractDomainService<TeamMember, TeamMemberRepository> {
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        super(teamMemberRepository, "TeamMember");
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public TeamMember save(TeamMember teamMember) {
        if (teamMemberRepository.existsByTwitter(teamMember.getTwitter())) {
            throw new AlreadyTakenException(MessageFormat.format(ErrorMessage.ALREADY_TAKEN, entityName, "twitter", teamMember.getTwitter()));
        }

        if (teamMemberRepository.existsByInstagram(teamMember.getInstagram())) {
            throw new AlreadyTakenException(MessageFormat.format(ErrorMessage.ALREADY_TAKEN, entityName, "instagram", teamMember.getInstagram()));
        }

        if (teamMemberRepository.existsByLinkedin(teamMember.getLinkedin())) {
            throw new AlreadyTakenException(MessageFormat.format(ErrorMessage.ALREADY_TAKEN, entityName, "linkedin", teamMember.getLinkedin()));
        }

        return teamMemberRepository.save(teamMember);
    }

    public TeamMember updateById(UUID teamMemberId, TeamMember updatedTeamMember) {
        TeamMember existingTeamMember = findById(teamMemberId);
        TeamMember existingTeamMemberUpdated = TeamMember.builder()
                .id(teamMemberId)
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

        return MessageFormat.format(ConfirmMessage.CONFIRM_DELETE, "team member");
    }
}
