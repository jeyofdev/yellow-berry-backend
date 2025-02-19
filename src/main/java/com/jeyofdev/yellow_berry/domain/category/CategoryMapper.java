package com.jeyofdev.yellow_berry.domain.category;

import com.jeyofdev.yellow_berry.domain.category.dto.CategoryDTO;
import com.jeyofdev.yellow_berry.domain.category.dto.SaveCategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO mapFromEntity(Category category);
    Category mapToEntity(SaveCategoryDTO saveCategoryDTO);
}
