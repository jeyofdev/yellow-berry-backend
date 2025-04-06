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
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.price", target = "priceDetails.price")
    @Mapping(source = "product.discount", target = "priceDetails.discount")
    ProductToCartDTO mapFromEntity(ProductToCart productToCart);

    ProductToCart mapToEntity(
            SaveProductToCartDTO saveProductToCartDTO,
            @Context CartService cartService
    );

    @AfterMapping
    default <T extends HasPriceDetails> void setPriceDiscount(@MappingTarget T dto) {
        if (dto.priceDetails() != null) {
            dto.priceDetails().setPriceDiscount();
        }
    }
}