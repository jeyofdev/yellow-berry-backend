package com.jeyofdev.yellow_berry.domain.profile;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfilePreviewDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.SaveProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ListResponseFormatMapper.class)
public interface ProfileMapper {
    @Mapping(target = "wishlist", source = "wishlist")
    @Mapping(source = "commentList", target = "comments", qualifiedByName = "toListResponseFormat")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.role", target = "role")
    @Mapping(source = "firstname", target = "nameDetails.firstname")
    @Mapping(source = "lastname", target = "nameDetails.lastname")
    @Mapping(target = "nameDetails.fullname", expression = "java(profile.getFirstname() + ' ' + profile.getLastname())")
    @Mapping(source = "address", target = "addressDetails.address")
    @Mapping(source = "region", target = "addressDetails.region")
    @Mapping(source = "department", target = "addressDetails.department")
    @Mapping(source = "zipCode", target = "addressDetails.city")
    @Mapping(source = "city", target = "addressDetails.zipCode")
    ProfileDTO mapFromEntity(Profile profile);

    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.role", target = "role")
    @Mapping(source = "firstname", target = "nameDetails.firstname")
    @Mapping(source = "lastname", target = "nameDetails.lastname")
    @Mapping(target = "nameDetails.fullname", expression = "java(profile.getFirstname() + ' ' + profile.getLastname())")
    @Mapping(source = "address", target = "addressDetails.address")
    @Mapping(source = "region", target = "addressDetails.region")
    @Mapping(source = "department", target = "addressDetails.department")
    @Mapping(source = "zipCode", target = "addressDetails.city")
    @Mapping(source = "city", target = "addressDetails.zipCode")
    ProfilePreviewDTO mapFromEntityPreview(Profile profile);

    @Mapping(target = "wishlist", ignore = true)
    Profile mapToEntity(SaveProfileDTO saveProfileDTO);
}
