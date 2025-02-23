package com.jeyofdev.yellow_berry.domain.comment;

import com.jeyofdev.yellow_berry.domain.comment.dto.CommentDTO;
import com.jeyofdev.yellow_berry.domain.comment.dto.SaveCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "profile", target = "profile")
    @Mapping(source = "profile.user.email", target = "profile.email")
    @Mapping(source = "profile.user.role", target = "profile.role")
    @Mapping(source = "profile.address", target = "profile.addressDetails.address")
    @Mapping(source = "profile.region", target = "profile.addressDetails.region")
    @Mapping(source = "profile.department", target = "profile.addressDetails.department")
    @Mapping(source = "profile.zipCode", target = "profile.addressDetails.zipCode")
    @Mapping(source = "profile.city", target = "profile.addressDetails.city")
    @Mapping(target = "profile.cart", ignore = true)
    CommentDTO mapFromEntity(Comment comment);

    Comment mapToEntity(SaveCommentDTO saveCommentDTO);
}
