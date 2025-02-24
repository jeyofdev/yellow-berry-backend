package com.jeyofdev.yellow_berry.domain.comment;

import com.jeyofdev.yellow_berry.domain.comment.dto.CommentDTO;
import com.jeyofdev.yellow_berry.domain.comment.dto.CommentPreviewDTO;
import com.jeyofdev.yellow_berry.domain.comment.dto.SaveCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "profile", target = "profile")
    @Mapping(source = "profile.user.email", target = "profile.email")
    @Mapping(source = "profile.firstname", target = "profile.nameDetails.firstname")
    @Mapping(source = "profile.lastname", target = "profile.nameDetails.lastname")
    @Mapping(target = "profile.nameDetails.fullname", expression = "java(profile.getFirstname() + ' ' +profile.getLastname())")
    @Mapping(target = "profile.addressDetails", ignore = true)
    @Mapping(target = "profile.phone", ignore = true)
    @Mapping(target = "profile.role", ignore = true)
    CommentDTO mapFromEntity(Comment comment);

    CommentPreviewDTO mapFromEntityPreview(Comment comment);

    Comment mapToEntity(SaveCommentDTO saveCommentDTO);
}
