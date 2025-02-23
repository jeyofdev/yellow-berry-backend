package com.jeyofdev.yellow_berry.domain.productDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.domain.product.Product;
import jakarta.persistence.*;
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
    private String description;

    @Column(name = "seller", columnDefinition = "VARCHAR(255)")
    private String seller;

    @Column(name = "service", columnDefinition = "VARCHAR(255)")
    private String service;

    @OneToOne(mappedBy = "productDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private Product product;
}
