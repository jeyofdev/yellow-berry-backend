package com.jeyofdev.yellow_berry.domain.wishlist;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.profile.ProfileMapper;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.SaveWishlistDTO;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.WishlistDTO;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.WishlistPreviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ListResponseFormatMapper.class, ProfileMapper.class})
public interface WishListMapper {
    @Mapping(source = "productList", target = "products.results")
    @Mapping(target = "profile.wishList", ignore = true)
    @Mapping(target = "profile.comments", ignore = true)
    @Mapping(source = "profile", target = "profile")
    @Mapping(source = "profile.user.email", target = "profile.email")
    @Mapping(source = "profile.user.role", target = "profile.role")
    @Mapping(source = "profile.firstname", target = "profile.nameDetails.firstname")
    @Mapping(source = "profile.lastname", target = "profile.nameDetails.lastname")
    @Mapping(target = "profile.nameDetails.fullname", expression = "java(profile.getFirstname() + ' ' +profile.getLastname())")
    @Mapping(target = "profile.phone", ignore = true)
    @Mapping(target = "profile.addressDetails", ignore = true)
    WishlistDTO mapFromEntity(WishList wishlist);

    @Mapping(source = "productList", target = "products.results")
    @Mapping(target = "profile.wishList", ignore = true)
    @Mapping(target = "profile.comments", ignore = true)
    @Mapping(source = "profile", target = "profile")
    @Mapping(source = "profile.user.email", target = "profile.email")
    @Mapping(source = "profile.user.role", target = "profile.role")
    @Mapping(source = "profile.firstname", target = "profile.nameDetails.firstname")
    @Mapping(source = "profile.lastname", target = "profile.nameDetails.lastname")
    @Mapping(target = "profile.nameDetails.fullname", expression = "java(profile.getFirstname() + ' ' +profile.getLastname())")
    @Mapping(target = "profile.phone", ignore = true)
    @Mapping(target = "profile.addressDetails", ignore = true)
    WishlistPreviewDTO mapFromEntityPreview(WishList wishlist);

    @Mapping(target = "profile", ignore = true)
    WishList mapToEntity(SaveWishlistDTO saveWishlistDTO);
}
