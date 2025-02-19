package com.jeyofdev.yellow_berry.domain.cart;

import com.jeyofdev.yellow_berry.domain.cart.dto.SaveCartDTO;
import com.jeyofdev.yellow_berry.domain.cart.dto.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDTO mapFromEntity(Cart cart);
    Cart mapToEntity(SaveCartDTO saveCartDTO);
}
