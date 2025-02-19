package com.jeyofdev.yellow_berry.domain.profile;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.SaveProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<ProfileDTO>>> findAllProfiles() {
        List<Profile> profileList = profileService.findAll();
        List<ProfileDTO> profileDTOList = profileList.stream().map(profileMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, profileDTOList);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<DomainSuccessResponse<ProfileDTO>> findProfileById(@PathVariable("profileId") UUID profileId) {
        Profile profile = profileService.findById(profileId);
        ProfileDTO profileDTO = profileMapper.mapFromEntity(profile);

        return DomainSuccessResponse.get(HttpStatus.OK, profileDTO);
    }

    @PostMapping
    public ResponseEntity<DomainSuccessResponse<ProfileDTO>> saveProfile(@RequestBody SaveProfileDTO saveProfileDTO) {
        Profile profile = profileMapper.mapToEntity(saveProfileDTO);
        Profile newProfile = profileService.save(profile);
        ProfileDTO newProfileDTO = profileMapper.mapFromEntity(newProfile);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newProfileDTO);
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<DomainSuccessResponse<ProfileDTO>> updateProfileById(
            @PathVariable("profileId") UUID profileId,
            @RequestBody SaveProfileDTO saveProfileDTO
    ) {
        Profile profile = profileMapper.mapToEntity(saveProfileDTO);
        Profile updateProfile = profileService.updateById(profileId, profile);
        ProfileDTO updateProfileDTO = profileMapper.mapFromEntity(updateProfile);

        return DomainSuccessResponse.get(HttpStatus.OK, updateProfileDTO);
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteProfileById(@PathVariable("profileId") UUID profileId) {
        String message = profileService.deleteById(profileId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
