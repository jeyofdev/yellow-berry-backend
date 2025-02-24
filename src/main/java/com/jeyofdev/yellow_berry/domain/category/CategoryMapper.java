package com.jeyofdev.yellow_berry.domain.category;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.category.dto.CategoryDTO;
import com.jeyofdev.yellow_berry.domain.category.dto.SaveCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ListResponseFormatMapper.class)
public interface CategoryMapper {
    @Mapping(source = "productList", target = "products", qualifiedByName = "toListResponseFormat")
    CategoryDTO mapFromEntity(Category category);

    Category mapToEntity(SaveCategoryDTO saveCategoryDTO);
}
