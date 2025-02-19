package com.jeyofdev.yellow_berry.domain.profile;

import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.SaveProfileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDTO mapFromEntity(Profile profile);
    Profile mapToEntity(SaveProfileDTO saveProfileDTO);
}
