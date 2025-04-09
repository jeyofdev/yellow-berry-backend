package com.jeyofdev.yellow_berry.domain.productToCart;

import com.jeyofdev.yellow_berry.core.interfaces.domain.model.HasPriceDetails;
import com.jeyofdev.yellow_berry.core.mappers.ListResponseFormatMapper;
import com.jeyofdev.yellow_berry.domain.cart.CartService;
import com.jeyofdev.yellow_berry.domain.productInformation.ProductInformationMapper;
import com.jeyofdev.yellow_berry.domain.productToCart.dto.ProductToCartDTO;
import com.jeyofdev.yellow_berry.domain.productToCart.dto.SaveProductToCartDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ListResponseFormatMapper.class, ProductInformationMapper.class})
public interface ProductToCartMapper {
    @Mapping(target = "weight", expression = "java(productToCart.getWeight().toString())")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.price", target = "priceDetails.price")
    @Mapping(source = "product.discount", target = "priceDetails.discount")
    ProductToCartDTO mapFromEntity(ProductToCart productToCart);

    ProductToCart mapToEntity(
            SaveProductToCartDTO saveProductToCartDTO,
            @Context CartService cartService
    );

    @Named("weightToString")
    default String weightToString(Double weight) {
        return weight != null ? weight.toString() : null;
    }

    @AfterMapping
    default <T extends HasPriceDetails> void setPriceDiscount(@MappingTarget T dto) {
        if (dto.priceDetails() != null) {
            dto.priceDetails().setPriceDiscount();
        }
    }
}