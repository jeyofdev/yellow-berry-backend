package com.jeyofdev.yellow_berry.domain.tag;

import com.jeyofdev.yellow_berry.domain.tag.dto.TagDTO;
import com.jeyofdev.yellow_berry.domain.tag.dto.SaveTagDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO mapFromEntity(Tag tag);
    Tag mapToEntity(SaveTagDTO saveTagDTO);
}
