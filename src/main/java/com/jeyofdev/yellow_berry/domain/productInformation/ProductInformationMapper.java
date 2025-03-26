package com.jeyofdev.yellow_berry.domain.productInformation;

import com.jeyofdev.yellow_berry.core.enums.ColorEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.domain.productInformation.dto.ProductInformationDTO;
import com.jeyofdev.yellow_berry.domain.productInformation.dto.SaveProductInformationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductInformationMapper {

    @Named("mapFromEntity")
    @Mapping(source = "weightList", target = "weightList", qualifiedByName = "mapWeightEnumListToStringList")
    @Mapping(source = "colorList", target = "colorList", qualifiedByName = "mapColorEnumListToStringList")
    ProductInformationDTO mapFromEntity(ProductInformation productInformation);

    ProductInformation mapToEntity(SaveProductInformationDTO saveProductInformationDTO);

    @Named("mapWeightEnumListToStringList")
    default List<String> mapWeightEnumListToStringList(List<WeightEnum> weightEnums) {
        return weightEnums.stream()
                .map(WeightEnum::toString)
                .collect(Collectors.toList());
    }

    @Named("mapColorEnumListToStringList")
    default List<String> mapColorEnumListToStringList(List<ColorEnum> colorEnums) {
        return colorEnums.stream()
                .map(ColorEnum::getLabel)
                .collect(Collectors.toList());
    }
}