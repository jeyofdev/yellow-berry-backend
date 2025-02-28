package com.jeyofdev.yellow_berry.domain.productDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
@Table(name = "product_details")
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotNull(message = ErrorMessage.REQUIRED_DESCRIPTION)
    private String description;

    @Column(name = "seller", columnDefinition = "VARCHAR(200)")
    @NotNull(message = ErrorMessage.REQUIRED_SELLER)
    @Size(min = 3, max = 200, message = ErrorMessage.VALID_SELLER)
    private String seller;

    @Column(name = "service", columnDefinition = "VARCHAR(200)")
    @NotNull(message = ErrorMessage.REQUIRED_SERVICE)
    @Size(min = 3, max = 200, message = ErrorMessage.VALID_SERVICE)
    private String service;

    @OneToOne(mappedBy = "productDetails", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    @NotNull(message = ErrorMessage.PRODUCT_NOT_NULL_PRODUCT_DETAILS)
    private Product product;
}
