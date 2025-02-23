package com.jeyofdev.yellow_berry.domain.profile;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.SaveProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ListResponseFormatMapper.class)
public interface ProfileMapper {
    @Mapping(target = "wishList", source = "wishlist")
    @Mapping(source = "commentList", target = "comments", qualifiedByName = "toListResponseFormat")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.role", target = "role")
    @Mapping(source = "address", target = "addressDetails.address")
    @Mapping(source = "region", target = "addressDetails.region")
    @Mapping(source = "department", target = "addressDetails.department")
    @Mapping(source = "zipCode", target = "addressDetails.city")
    @Mapping(source = "city", target = "addressDetails.zipCode")
    ProfileDTO mapFromEntity(Profile profile);

    @Mapping(target = "wishlist", ignore = true)
    Profile mapToEntity(SaveProfileDTO saveProfileDTO);
}
