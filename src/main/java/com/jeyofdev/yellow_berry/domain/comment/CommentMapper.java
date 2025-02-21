package com.jeyofdev.yellow_berry.domain.comment;

import com.jeyofdev.yellow_berry.domain.comment.dto.CommentDTO;
import com.jeyofdev.yellow_berry.domain.comment.dto.SaveCommentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDTO mapFromEntity(Comment comment);

    Comment mapToEntity(SaveCommentDTO saveCommentDTO);
}
