package com.jeyofdev.yellow_berry.domain.product;

import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.category.CategoryService;
import com.jeyofdev.yellow_berry.domain.comment.Comment;
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
    @Mapping(target = "categoryList", source = "categoryIds", qualifiedByName = "mapCategoryIdsToCategories")
    @Mapping(target = "commentList", source = "commentIds", qualifiedByName = "mapCommentIdsToComments")
    Product mapToEntity(SaveProductDTO saveProductDTO, @Context TagService tagService, @Context CategoryService categoryService);

    @Named("mapTagIdsToTags")
    default List<Tag> mapTagIdsToTags(List<UUID> tagIds, @Context TagService tagService) {
        return tagService.getTagsByIds(tagIds);
    }

    @Named("mapCategoryIdsToCategories")
    default List<Category> mapCategoryIdsToCategories(List<UUID> categoryIds, @Context CategoryService categoryService) {
        return categoryService.getCategoriesByIds(categoryIds);
    }

    @Named("mapCommentIdsToComments")
    default List<Comment> mapCommentIdsToComments(List<UUID> commentIds) {
        return List.of();
    }
}
