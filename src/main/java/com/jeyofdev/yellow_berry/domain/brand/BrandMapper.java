package com.jeyofdev.yellow_berry.domain.brand;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.brand.dto.SaveBrandDTO;
import com.jeyofdev.yellow_berry.domain.brand.dto.BrandDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ListResponseFormatMapper.class)
public interface BrandMapper {
    @Mapping(source = "productList", target = "products", qualifiedByName = "toListResponseFormat")
    BrandDTO mapFromEntity(Brand brand);

    Brand mapToEntity(SaveBrandDTO saveBrandDTO);
}
