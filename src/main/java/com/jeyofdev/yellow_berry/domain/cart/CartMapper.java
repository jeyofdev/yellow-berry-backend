package com.jeyofdev.yellow_berry.domain.cart;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.cart.dto.SaveCartDTO;
import com.jeyofdev.yellow_berry.domain.cart.dto.CartDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ListResponseFormatMapper.class)
public interface CartMapper {
    @Mapping(source = "productList", target = "products", qualifiedByName = "toListResponseFormat")
    CartDTO mapFromEntity(Cart cart);

    Cart mapToEntity(SaveCartDTO saveCartDTO);
}
