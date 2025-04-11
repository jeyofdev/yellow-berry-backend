package com.jeyofdev.yellow_berry.domain.tag;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.product.ProductMapper;
import com.jeyofdev.yellow_berry.domain.tag.dto.SaveTagDTO;
import com.jeyofdev.yellow_berry.domain.tag.dto.TagDTO;
import com.jeyofdev.yellow_berry.domain.tag.dto.TagPreviewDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ListResponseFormatMapper.class, ProductMapper.class})
public interface TagMapper {
    @Mapping(source = "productList", target = "products", qualifiedByName = "mapProductsPreviewToDTO")
    TagDTO mapFromEntity(Tag tag, @Context ProductMapper productMapper);

    TagPreviewDTO mapFromEntityPreview(Tag tag);

    Tag mapToEntity(SaveTagDTO saveTagDTO);
}
