package com.jeyofdev.yellow_berry.domain.profile;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.core.model.NameFormat;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfilePreviewDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.SaveProfileDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ListResponseFormatMapper.class)
public interface ProfileMapper {
    @Mapping(target = "wishlist", source = "wishlist")
    @Mapping(source = "commentList", target = "comments", qualifiedByName = "toListResponseFormat")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.role", target = "role")
    @Mapping(source = "firstname", target = "nameDetails.firstname")
    @Mapping(source = "lastname", target = "nameDetails.lastname")
    @Mapping(source = "address", target = "addressDetails.address")
    @Mapping(source = "city", target = "addressDetails.city")
    @Mapping(source = "zipCode", target = "addressDetails.zipCode")
    @Mapping(source = "department", target = "addressDetails.department")
    @Mapping(source = "region", target = "addressDetails.region")
    ProfileDTO mapFromEntity(Profile profile);

    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.role", target = "role")
    @Mapping(source = "firstname", target = "nameDetails.firstname")
    @Mapping(source = "lastname", target = "nameDetails.lastname")
    @Mapping(source = "address", target = "addressDetails.address")
    @Mapping(source = "city", target = "addressDetails.city")
    @Mapping(source = "zipCode", target = "addressDetails.zipCode")
    @Mapping(source = "department", target = "addressDetails.department")
    @Mapping(source = "region", target = "addressDetails.region")
    ProfilePreviewDTO mapFromEntityPreview(Profile profile);

    @Mapping(target = "wishlist", ignore = true)
    Profile mapToEntity(SaveProfileDTO saveProfileDTO);

    @AfterMapping
    default void setFullName(@MappingTarget ProfileDTO profileDTO) {
        if (profileDTO.nameDetails() != null) {
            profileDTO.nameDetails().setFullname();
        } else {
            NameFormat nameFormat = new NameFormat();
            nameFormat.setFullname();
        }
    }
}
