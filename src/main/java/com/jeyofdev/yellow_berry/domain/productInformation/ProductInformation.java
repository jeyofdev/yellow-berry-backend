package com.jeyofdev.yellow_berry.domain.productInformation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.core.enums.ColorEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.domain.product.Product;
import jakarta.persistence.*;
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
    @Column(name = "weight", columnDefinition = "VARCHAR(255)")
    private WeightEnum weight;

    @Column(name = "dimensions", columnDefinition = "VARCHAR(20)")
    private String dimension;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", columnDefinition = "VARCHAR(255)")
    private ColorEnum color;

    @Column(name = "brand", columnDefinition = "VARCHAR(100)")
    private String brand;

    @Column(name = "quantity", columnDefinition = "INT")
    private Integer quantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonIgnore
    private Product product;
}
