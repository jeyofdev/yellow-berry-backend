package com.jeyofdev.yellow_berry.domain.team_member;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.team_member.dto.SaveTeamMemberDTO;
import com.jeyofdev.yellow_berry.domain.team_member.dto.TeamMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/team/member")
@RequiredArgsConstructor
public class TeamMemberController {
    private final TeamMemberService teamMemberService;
    private final TeamMemberMapper teamMemberMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<TeamMemberDTO>>> findAllTeamMembers() {
        List<TeamMember> teamMemberMemberList = teamMemberService.findAll();
        List<TeamMemberDTO> teamMemberDTOList = teamMemberMemberList.stream().map(teamMemberMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, teamMemberDTOList);
    }

    @GetMapping("/{teamMemberId}")
    public ResponseEntity<DomainSuccessResponse<TeamMemberDTO>> findTeamMemberById(@PathVariable("teamMemberId") UUID teamMemberId) {
        TeamMember teamMemberMember = teamMemberService.findById(teamMemberId);
        TeamMemberDTO teamMemberDTO = teamMemberMapper.mapFromEntity(teamMemberMember);

        return DomainSuccessResponse.get(HttpStatus.OK, teamMemberDTO);
    }

    @PostMapping
    public ResponseEntity<DomainSuccessResponse<TeamMemberDTO>> saveTeamMember(@RequestBody SaveTeamMemberDTO saveTeamMemberDTO) {
        TeamMember teamMemberMember = teamMemberMapper.mapToEntity(saveTeamMemberDTO);
        TeamMember newTeamMemberMember = teamMemberService.save(teamMemberMember);
        TeamMemberDTO newTeamMemberDTO = teamMemberMapper.mapFromEntity(newTeamMemberMember);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newTeamMemberDTO);
    }

    @PutMapping("/{teamMemberId}")
    public ResponseEntity<DomainSuccessResponse<TeamMemberDTO>> updateTeamMemberById(
            @PathVariable("teamMemberId") UUID teamMemberId,
            @RequestBody SaveTeamMemberDTO saveTeamMemberDTO
    ) {
        TeamMember teamMemberMember = teamMemberMapper.mapToEntity(saveTeamMemberDTO);
        TeamMember updateTeamMemberMember = teamMemberService.updateById(teamMemberId, teamMemberMember);
        TeamMemberDTO updateTeamMemberDTO = teamMemberMapper.mapFromEntity(updateTeamMemberMember);

        return DomainSuccessResponse.get(HttpStatus.OK, updateTeamMemberDTO);
    }

    @DeleteMapping("/{teamMemberId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteTeamMemberById(@PathVariable("teamMemberId") UUID teamMemberId) {
        String message = teamMemberService.deleteById(teamMemberId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
