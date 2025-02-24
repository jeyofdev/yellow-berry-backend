package com.jeyofdev.yellow_berry.domain.brand;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.brand.dto.BrandDTO;
import com.jeyofdev.yellow_berry.domain.brand.dto.BrandPreviewDTO;
import com.jeyofdev.yellow_berry.domain.brand.dto.SaveBrandDTO;
import com.jeyofdev.yellow_berry.domain.product.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ListResponseFormatMapper.class, ProductMapper.class})
public interface BrandMapper {
    @Mapping(source = "productList", target = "products.results")
    BrandDTO mapFromEntity(Brand brand);

    BrandPreviewDTO mapFromEntityPreview(Brand brand);

    Brand mapToEntity(SaveBrandDTO saveBrandDTO);
}
