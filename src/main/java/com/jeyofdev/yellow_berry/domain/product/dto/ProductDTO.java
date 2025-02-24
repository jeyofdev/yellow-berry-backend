package com.jeyofdev.yellow_berry.domain.product.dto;

import com.jeyofdev.yellow_berry.core.enums.StockEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.core.model.PriceFormat;
import com.jeyofdev.yellow_berry.domain.productDetails.ProductDetails;
import com.jeyofdev.yellow_berry.domain.productInformation.ProductInformation;
import com.jeyofdev.yellow_berry.domain.brand.Brand;
import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.comment.Comment;
import com.jeyofdev.yellow_berry.domain.tag.Tag;

import java.util.UUID;

public record ProductDTO(
        UUID id,
        String name,
        Integer rating,
        String description,
        PriceFormat priceDetails,
        StockEnum stock,
        WeightEnum weight,
        ProductDetails details,
        ProductInformation informations,
        ListResponseFormat<Tag> tags,
        ListResponseFormat<Category> categories,
        ListResponseFormat<Comment> comments,
        Brand brand
) {
}
