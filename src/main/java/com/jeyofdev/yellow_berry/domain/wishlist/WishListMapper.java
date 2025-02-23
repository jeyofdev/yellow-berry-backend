package com.jeyofdev.yellow_berry.domain.wishlist;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.SaveWishlistDTO;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.WishlistDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ListResponseFormatMapper.class)
public interface WishListMapper {
    @Mapping(source = "productList", target = "products", qualifiedByName = "toListResponseFormat")
    @Mapping(target = "profile.wishList", ignore = true)
    @Mapping(target = "profile.comments", ignore = true)
    @Mapping(target = "profile.cart", ignore = true)
    WishlistDTO mapFromEntity(WishList wishlist);

    @Mapping(target = "profile", ignore = true)
    WishList mapToEntity(SaveWishlistDTO saveWishlistDTO);
}
