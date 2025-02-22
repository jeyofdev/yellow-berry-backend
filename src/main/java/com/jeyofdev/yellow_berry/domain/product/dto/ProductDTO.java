package com.jeyofdev.yellow_berry.domain.product.dto;

import com.jeyofdev.yellow_berry.core.enums.StockEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.domain.brand.Brand;
import com.jeyofdev.yellow_berry.domain.cart.Cart;
import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.comment.Comment;
import com.jeyofdev.yellow_berry.domain.productDetails.ProductDetails;
import com.jeyofdev.yellow_berry.domain.productInformation.ProductInformation;
import com.jeyofdev.yellow_berry.domain.tag.Tag;
import com.jeyofdev.yellow_berry.domain.wishlist.WishList;

import java.util.List;
import java.util.UUID;

public record ProductDTO(
        UUID id,
        String name,
        Integer rating,
        String description,
        Double price,
        Double priceDiscount,
        Double discount,
        StockEnum stock,
        WeightEnum weight,
        List<Tag> tagList,
        List<Category> categoryList,
        List<Comment> commentList,
        ProductDetails productDetails,
        ProductInformation productInformation,
        List<WishList> wishLists,
        List<Cart> cartList,
        Brand brand
) {
}
