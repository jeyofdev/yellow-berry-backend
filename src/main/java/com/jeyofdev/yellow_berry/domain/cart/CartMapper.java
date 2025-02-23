package com.jeyofdev.yellow_berry.domain.cart;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.cart.dto.SaveCartDTO;
import com.jeyofdev.yellow_berry.domain.cart.dto.CartDTO;
import com.jeyofdev.yellow_berry.domain.product.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ListResponseFormatMapper.class, ProductMapper.class})
public interface CartMapper {

    @Mapping(source = "productList", target = "productList.results")
    CartDTO mapFromEntity(Cart cart);

    Cart mapToEntity(SaveCartDTO saveCartDTO);
}