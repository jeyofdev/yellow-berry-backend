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
    @Mapping(source = "profile", target = "profile")
    @Mapping(source = "profile.user.email", target = "profile.email")
    @Mapping(source = "profile.user.role", target = "profile.role")
    @Mapping(source = "profile.address", target = "profile.addressDetails.address")
    @Mapping(source = "profile.region", target = "profile.addressDetails.region")
    @Mapping(source = "profile.department", target = "profile.addressDetails.department")
    @Mapping(source = "profile.zipCode", target = "profile.addressDetails.zipCode")
    @Mapping(source = "profile.city", target = "profile.addressDetails.city")
    WishlistDTO mapFromEntity(WishList wishlist);

    @Mapping(target = "profile", ignore = true)
    WishList mapToEntity(SaveWishlistDTO saveWishlistDTO);
}
