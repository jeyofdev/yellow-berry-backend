package com.jeyofdev.yellow_berry.domain.wishlist;

import com.jeyofdev.yellow_berry.domain.wishlist.dto.SaveWishlistDTO;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.WishlistDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishListMapper {
    WishlistDTO mapFromEntity(WishList wishlist);

    @Mapping(target = "profile", ignore = true)
    WishList mapToEntity(SaveWishlistDTO saveWishlistDTO);
}
