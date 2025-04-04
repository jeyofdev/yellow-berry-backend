package com.jeyofdev.yellow_berry.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeyofdev.yellow_berry.core.interfaces.domain.model.HasPriceDetails;
import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.core.model.PriceFormat;
import com.jeyofdev.yellow_berry.core.model.RatingFormat;
import com.jeyofdev.yellow_berry.domain.brand.dto.BrandDTO;
import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.comment.dto.CommentDTO;
import com.jeyofdev.yellow_berry.domain.productDetails.ProductDetails;
import com.jeyofdev.yellow_berry.domain.productInformation.dto.ProductInformationDTO;
import com.jeyofdev.yellow_berry.domain.tag.Tag;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDTO(
        UUID id,
        String name,
        String reference,
        RatingFormat ratingDetails,
        PriceFormat priceDetails,
        String stock,
        ProductDetails details,
        ProductInformationDTO informations,
        ListResponseFormat<Tag> tags,
        ListResponseFormat<Category> categories,
        ListResponseFormat<CommentDTO> comments,
        BrandDTO brand
) implements HasPriceDetails {

}
