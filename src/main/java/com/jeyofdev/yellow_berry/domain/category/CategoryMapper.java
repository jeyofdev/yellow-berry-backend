package com.jeyofdev.yellow_berry.domain.category;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.category.dto.CategoryDTO;
import com.jeyofdev.yellow_berry.domain.category.dto.CategoryPreviewDTO;
import com.jeyofdev.yellow_berry.domain.category.dto.SaveCategoryDTO;
import com.jeyofdev.yellow_berry.domain.product.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ListResponseFormatMapper.class, ProductMapper.class})
public interface CategoryMapper {
    @Mapping(source = "productList", target = "products.results")
    CategoryDTO mapFromEntity(Category category);

    @Mapping(source = "productList", target = "products.results")
    CategoryPreviewDTO mapFromEntityPreview(Category category);

    Category mapToEntity(SaveCategoryDTO saveCategoryDTO);
}
