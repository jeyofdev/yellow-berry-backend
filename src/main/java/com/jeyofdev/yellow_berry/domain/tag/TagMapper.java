package com.jeyofdev.yellow_berry.domain.tag;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.tag.dto.TagDTO;
import com.jeyofdev.yellow_berry.domain.tag.dto.SaveTagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ListResponseFormatMapper.class)
public interface TagMapper {
    @Mapping(source = "productList", target = "products", qualifiedByName = "toListResponseFormat")
    TagDTO mapFromEntity(Tag tag);

    Tag mapToEntity(SaveTagDTO saveTagDTO);
}
