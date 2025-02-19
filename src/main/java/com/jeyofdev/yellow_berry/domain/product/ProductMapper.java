package com.jeyofdev.yellow_berry.domain.product;

import com.jeyofdev.yellow_berry.domain.product.dto.SaveProductDTO;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO mapFromEntity(Product product);
    Product mapToEntity(SaveProductDTO saveProductDTO);
}
