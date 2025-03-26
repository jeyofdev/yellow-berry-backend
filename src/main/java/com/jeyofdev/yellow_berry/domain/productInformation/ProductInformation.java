package com.jeyofdev.yellow_berry.domain.productInformation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.annotation.ValidEnum;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.core.converter.ColorEnumListConverter;
import com.jeyofdev.yellow_berry.core.enums.ColorEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.domain.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "product_information")
public class ProductInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "weight", columnDefinition = "VARCHAR(30)")
    @NotNull(message = ErrorMessage.REQUIRED_WEIGHT)
    @ValidEnum(enumClass = WeightEnum.class, message = ErrorMessage.VALID_WEIGHT)
    private WeightEnum weight;

    @Column(name = "dimensions", columnDefinition = "VARCHAR(20)")
    @NotNull(message = ErrorMessage.REQUIRED_DIMENSIONS)
    @Size(min = 12, max = 20, message = ErrorMessage.VALID_DIMENSIONS)
    private String dimension;

    @Convert(converter = ColorEnumListConverter.class)
    @Column(name = "colors", columnDefinition = "TEXT")
    @NotNull(message = ErrorMessage.REQUIRED_COLOR)
    private List<ColorEnum> colorList;


    @Column(name = "quantity", columnDefinition = "INT")
    @NotNull(message = ErrorMessage.REQUIRED_QUANTITY)
    @Min(value = 1, message = ErrorMessage.MIN_QUANTITY)
    @Max(value = 100, message = ErrorMessage.MAX_QUANTITY)
    private Integer quantity;

    @OneToOne(mappedBy = "productInformation", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    @NotNull(message = ErrorMessage.PRODUCT_NOT_NULL_PRODUCT_INFORMATION)
    private Product product;
}
