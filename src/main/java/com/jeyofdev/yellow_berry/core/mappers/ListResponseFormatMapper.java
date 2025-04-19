package com.jeyofdev.yellow_berry.core.mappers;

import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductMapper;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductPreviewDTO;
import com.jeyofdev.yellow_berry.domain.productToCart.ProductToCart;
import com.jeyofdev.yellow_berry.domain.productToCart.ProductToCartMapper;
import com.jeyofdev.yellow_berry.domain.productToCart.dto.ProductToCartDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ListResponseFormatMapper {
    @Named("toListResponseFormat")
    default <T> ListResponseFormat<T> toListResponseFormat(List<T> list) {
        return new ListResponseFormat<>(list);
    }

    @Named("mapProductsPreviewToDTO")
    default ListResponseFormat<ProductPreviewDTO> mapProductsPreviewToDTO(List<Product> products, @Context ProductMapper productMapper) {
        if (products == null || products.isEmpty()) {
            return new ListResponseFormat<>(new ArrayList<>());
        }

        List<ProductPreviewDTO> dtos = products.stream()
                .map(productMapper::mapFromEntityPreview)
                .toList();
        return new ListResponseFormat<>(dtos);
    }

    @Named("mapProductsToCartToDTO")
    default ListResponseFormat<ProductToCartDTO> mapProductsToCartToDTO(List<ProductToCart> products, @Context ProductToCartMapper productToCartMapper) {
        List<ProductToCartDTO> dtos = products.stream()
                .map(productToCartMapper::mapFromEntity)
                .toList();
        return new ListResponseFormat<>(dtos);
    }
}