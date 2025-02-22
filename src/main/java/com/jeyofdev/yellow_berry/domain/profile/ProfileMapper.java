package com.jeyofdev.yellow_berry.domain.profile;

import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.SaveProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @Mapping(target = "wishList", source = "wishlist")
    ProfileDTO mapFromEntity(Profile profile);

    @Mapping(target = "wishlist", ignore = true)
    Profile mapToEntity(SaveProfileDTO saveProfileDTO);
}
