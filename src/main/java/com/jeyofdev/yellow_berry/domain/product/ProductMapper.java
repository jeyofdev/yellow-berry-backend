package com.jeyofdev.yellow_berry.domain.product;

import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.brand.Brand;
import com.jeyofdev.yellow_berry.domain.brand.BrandService;
import com.jeyofdev.yellow_berry.domain.cart.Cart;
import com.jeyofdev.yellow_berry.domain.cart.CartService;
import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.category.CategoryService;
import com.jeyofdev.yellow_berry.domain.comment.Comment;
import com.jeyofdev.yellow_berry.core.interfaces.domain.model.HasPriceDetails;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductDTO;
import com.jeyofdev.yellow_berry.domain.product.dto.ProductPreviewDTO;
import com.jeyofdev.yellow_berry.domain.product.dto.SaveProductDTO;
import com.jeyofdev.yellow_berry.domain.tag.Tag;
import com.jeyofdev.yellow_berry.domain.tag.TagService;
import com.jeyofdev.yellow_berry.domain.wishlist.WishList;
import com.jeyofdev.yellow_berry.domain.wishlist.WishlistService;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = ListResponseFormatMapper.class)
public interface ProductMapper {
    @Mapping(source = "tagList", target = "tags", qualifiedByName = "toListResponseFormat")
    @Mapping(source = "categoryList", target = "categories", qualifiedByName = "toListResponseFormat")
    @Mapping(source = "commentList", target = "comments", qualifiedByName = "toListResponseFormat")
    @Mapping(source = "price", target = "priceDetails.price")
    @Mapping(source = "discount", target = "priceDetails.discount")
    @Mapping(source = "productDetails", target = "details")
    @Mapping(source = "productInformation", target = "informations")
    @Mapping(target = "weight", expression = "java(product.getWeight().toString())")
    ProductDTO mapFromEntity(Product product);

    @Mapping(source = "productDetails.description", target = "description")
    @Mapping(source = "categoryList", target = "categories", qualifiedByName = "toListResponseFormat")
    @Mapping(source = "price", target = "priceDetails.price")
    @Mapping(source = "discount", target = "priceDetails.discount")
    @Mapping(target = "commentCount", expression = "java(product.getCommentList().size())")
    @Mapping(target = "weight", expression = "java(product.getWeight().toString())")
    ProductPreviewDTO mapFromEntityPreview(Product product);

    @Mapping(target = "tagList", source = "tagIds", qualifiedByName = "mapTagIdsToTags")
    @Mapping(target = "categoryList", source = "categoryIds", qualifiedByName = "mapCategoryIdsToCategories")
    @Mapping(target = "commentList", source = "commentIds", qualifiedByName = "mapCommentIdsToComments")
    @Mapping(target = "brand", source = "brandId", qualifiedByName = "mapBrandIdToBrand")
    Product mapToEntity(
            SaveProductDTO saveProductDTO,
            @Context TagService tagService,
            @Context CategoryService categoryService,
            @Context WishlistService wishListService,
            @Context CartService cartService,
            @Context BrandService brandService
    );

    @Named("mapTagIdsToTags")
    default List<Tag> mapTagIdsToTags(List<UUID> tagIds, @Context TagService tagService) {
        return tagService.getTagsByIds(tagIds);
    }

    @Named("mapCategoryIdsToCategories")
    default List<Category> mapCategoryIdsToCategories(List<UUID> categoryIds, @Context CategoryService categoryService) {
        return categoryService.getCategoriesByIds(categoryIds);
    }

    @Named("mapCommentIdsToComments")
    default List<Comment> mapCommentIdsToComments(List<UUID> commentIds) {
        return List.of();
    }

    @Named("mapWishlistIdsToWishlists")
    default List<WishList> mapWishlistIdsToWishlists(List<UUID> wishlistIds, @Context WishlistService wishlistService) {
        return wishlistService.getWishlistsByIds(wishlistIds);
    }

    @Named("mapCartListIdsToCart")
    default List<Cart> mapCartListIdsToCart(List<UUID> cartListIds, @Context CartService cartservice) {
        return cartservice.getCartListByIds(cartListIds);
    }

    @Named("mapBrandIdToBrand")
    default Brand mapBrandIdToBrand(UUID brandId, @Context BrandService brandService) {
        return brandId != null ? brandService.findById(brandId) : null;
    }

    @AfterMapping
    default <T extends HasPriceDetails> void setFullName(@MappingTarget T dto) {
        if (dto.priceDetails() != null) {
            dto.priceDetails().setPriceDiscount();
        }
    }

}