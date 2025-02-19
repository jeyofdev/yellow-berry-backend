package com.jeyofdev.yellow_berry.domain.productDetails;

import com.jeyofdev.yellow_berry.domain.productDetails.dto.ProductDetailsDTO;
import com.jeyofdev.yellow_berry.domain.productDetails.dto.SaveProductDetailsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDetailsMapper {
    ProductDetailsDTO mapFromEntity(ProductDetails productDetails);
    ProductDetails mapToEntity(SaveProductDetailsDTO saveProductDetailsDTO);
}
