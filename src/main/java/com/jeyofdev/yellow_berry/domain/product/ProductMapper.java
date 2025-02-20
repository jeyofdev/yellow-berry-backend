package com.jeyofdev.yellow_berry.domain.product;

import com.jeyofdev.yellow_berry.domain.product.dto.SaveProductDTO;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductDTO;
import com.jeyofdev.yellow_berry.domain.tag.Tag;
import com.jeyofdev.yellow_berry.domain.tag.TagService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO mapFromEntity(Product product);

    @Mapping(target = "tagList", source = "tagIds", qualifiedByName = "mapTagIdsToTags")
    Product mapToEntity(SaveProductDTO saveProductDTO, @Context TagService tagService);

    @Named("mapTagIdsToTags")
    default List<Tag> mapTagIdsToTags(List<UUID> tagIds, @Context TagService tagService) {
        return tagService.getTagsByIds(tagIds);
    }
}
