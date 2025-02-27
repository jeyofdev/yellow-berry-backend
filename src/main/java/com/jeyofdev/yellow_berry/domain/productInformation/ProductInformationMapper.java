package com.jeyofdev.yellow_berry.domain.productInformation;

import com.jeyofdev.yellow_berry.domain.productInformation.dto.ProductInformationDTO;
import com.jeyofdev.yellow_berry.domain.productInformation.dto.SaveProductInformationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductInformationMapper {
    ProductInformationDTO mapFromEntity(ProductInformation productInformation);

    ProductInformation mapToEntity(SaveProductInformationDTO saveProductInformationDTO);
}
