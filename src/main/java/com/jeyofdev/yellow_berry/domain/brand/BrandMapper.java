package com.jeyofdev.yellow_berry.domain.brand;

import com.jeyofdev.yellow_berry.domain.brand.dto.SaveBrandDTO;
import com.jeyofdev.yellow_berry.domain.brand.dto.BrandDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    @Mapping(target = "productList", source = "productList")
    BrandDTO mapFromEntity(Brand brand);

    Brand mapToEntity(SaveBrandDTO saveBrandDTO);
}
